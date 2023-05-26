package com.benitomiyazato.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @Email(message = "Invalid Email")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
