package com.benitomiyazato.controller;

import com.benitomiyazato.dto.AuthenticationRequest;
import com.benitomiyazato.dto.AuthenticationResponse;
import com.benitomiyazato.dto.UserResponse;
import com.benitomiyazato.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final WebClient.Builder webClient;

    public AuthenticationResponse login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        // TODO: fetch user data from user service
        UserResponse userResponse = webClient.build().get()
                .uri("http://user-service/api/users/" + authenticationRequest.getEmail())
                .retrieve()
                .bodyToMono(UserResponse.class).block();



        //  TODO: check for email and password
        //      generate JWT
        return null; // return JWT
    }

    // TODO: public void validateToken( get token from Authorization Header ) {
    //  return status code = 200 if valid, if not 403 (forbidden)
    //  }

    @GetMapping
    public String test() {
        return "working";
    }

}
