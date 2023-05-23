package com.benitomiyazato.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USER")
public class UserModel {

    @Id
    @GeneratedValue
    private UUID userId;

    @Column(unique = true)
    private String email;

    private String fullName;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String phone;

    private String address;

    private String cep;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
