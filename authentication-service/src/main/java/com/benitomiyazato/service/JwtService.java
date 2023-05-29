package com.benitomiyazato.service;

import com.benitomiyazato.config.RsaKeyProperties;
import lombok.RequiredArgsConstructor;
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

    public String generateToken(UserDetails user, int expirationTimeInHours) {
        Instant now = Instant.now();
        String scope = user.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(expirationTimeInHours, ChronoUnit.HOURS))
                .subject(user.getUsername())
                .claim("scope", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String validate(String token) {
        NimbusJwtDecoder build = NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
        return build.decode(token).getSubject();
    }
}
