package com.benitomiyazato.service;

import com.benitomiyazato.config.RsaKeyProperties;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final RsaKeyProperties rsaKeys;
    Logger logger = LogManager.getLogger(JwtService.class);


    public String generateToken(UserDetails user, int expirationTimeInHours) {
        Instant now = Instant.now();
        String scope = user.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        logger.info("Token scope: {}", scope);


        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(expirationTimeInHours, ChronoUnit.HOURS))
                .subject(user.getUsername())
                .claim("scope", scope)
                .build();
        logger.info("Generating token with expiration of {} hours", expirationTimeInHours);
        logger.info("Subject: {}", user.getUsername());
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String validate(String token) {
        NimbusJwtDecoder build = NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
        try {
            return build.decode(token).getSubject();
        } catch (JwtException e) {
            e.printStackTrace();
            logger.error("Token is invalid");
            return null;
        }
    }
}
