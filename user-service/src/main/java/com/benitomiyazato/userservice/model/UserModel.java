package com.benitomiyazato.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USER")
public class UserModel {

    @Id
    @GeneratedValue
    private UUID userId;

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
