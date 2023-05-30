package com.benitomiyazato.service;

import com.benitomiyazato.dto.AuthenticationRequest;
import com.benitomiyazato.dto.AuthenticationResponse;
import com.benitomiyazato.dto.UserAuthResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final WebClient.Builder webClient;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    Logger logger = LogManager.getLogger(AuthenticationService.class);


    @Value("${jwt.expirationTimeInHours}")
    private int expirationTimeInHours;


    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        logger.info("Fetching auth object for password and email verification");
        UserAuthResponse userAuthResponse = webClient.build().get()
                .uri("http://user-service/api/users/auth/" + authenticationRequest.getEmail())
                .retrieve()
                .bodyToMono(UserAuthResponse.class).block();

        if (userAuthResponse == null) {
            logger.error("User not found with email: {}", authenticationRequest.getEmail());
            throw new IllegalArgumentException("userAuthResponse null");
        }

        if(!encoder.matches(authenticationRequest.getPassword(), userAuthResponse.getEncodedPassword())) {
            logger.error("Invalid username or password");
            throw new IllegalArgumentException("Invalid username or password");
        }

        logger.info("User validated, generating token...");
        return new AuthenticationResponse(jwtService.generateToken(userAuthResponse, expirationTimeInHours));
    }

    public String validate(String token) {
        logger.info("Validating token: {}", token);
        return jwtService.validate(token);
    }
}
