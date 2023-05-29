package com.benitomiyazato.controller;

import com.benitomiyazato.dto.AuthenticationRequest;
import com.benitomiyazato.dto.AuthenticationResponse;
import com.benitomiyazato.dto.UserResponse;
import com.benitomiyazato.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    public String login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return authenticationService.login(authenticationRequest);
    }

    // TODO: public void validateToken( get token from Authorization Header ) {
    //  return status code = 200 if valid, if not 403 (forbidden)
    //  }

    @GetMapping
    public String test() {
        return "working";
    }

}
