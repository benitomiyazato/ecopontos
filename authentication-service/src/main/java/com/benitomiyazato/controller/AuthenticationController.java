package com.benitomiyazato.controller;

import com.benitomiyazato.dto.AuthenticationRequest;
import com.benitomiyazato.dto.AuthenticationResponse;
import com.benitomiyazato.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return authenticationService.login(authenticationRequest);
    }

    @GetMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public String validateToken(@RequestParam String token) {
        return authenticationService.validate(token);
    }

    @GetMapping
    public String test() {
        return "working";
    }

}
