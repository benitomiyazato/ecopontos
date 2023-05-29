package com.benitomiyazato.service;

import com.benitomiyazato.dto.AuthenticationRequest;
import com.benitomiyazato.dto.AuthenticationResponse;
import com.benitomiyazato.dto.UserAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final WebClient.Builder webClient;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;


    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        // TODO: fetch user data from user service
        UserAuthResponse userAuthResponse = webClient.build().get()
                .uri("http://user-service/api/users/auth/" + authenticationRequest.getEmail())
                .retrieve()
                .bodyToMono(UserAuthResponse.class).block();

        if (userAuthResponse == null)
            throw new IllegalArgumentException("userAuthResponse null");

        if(!encoder.matches(authenticationRequest.getPassword(), userAuthResponse.getEncodedPassword()))
            throw new IllegalArgumentException("senha errada");

        return new AuthenticationResponse(userAuthResponse.getUsername() + userAuthResponse.getPassword() + userAuthResponse.getAuthorities());
    }
}
