package com.benitomiyazato.service;

import com.benitomiyazato.dto.AuthenticationRequest;
import com.benitomiyazato.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final WebClient.Builder webClient;


    public String login(AuthenticationRequest authenticationRequest) {
        // TODO: fetch user data from user service
        UserResponse userResponse = webClient.build().get()
                .uri("http://user-service/api/users/byEmail/" + authenticationRequest.getEmail())
                .retrieve()
                .bodyToMono(UserResponse.class).block();

        if (userResponse == null)
            throw new IllegalArgumentException("CAPIM NA PALHETA");



        //  TODO: check for email and password
        //      generate JWT
//        return null; // return JWT
        return userResponse.getFullName();
    }
}
