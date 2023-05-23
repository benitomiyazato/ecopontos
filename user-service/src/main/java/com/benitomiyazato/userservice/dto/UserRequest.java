package com.benitomiyazato.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRequest {

    @Email(message = "Invalid Email")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Full Name is required")
    private String fullName;

    @NotBlank(message = "Cpf is required")
    private String cpf;

    @NotBlank(message = "Phone number is required")
    private String phone;

    private String address;
    private String cep;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
