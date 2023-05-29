package com.benitomiyazato.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private UUID userId;
    private String email;
    private String fullName;
    private String cpf;
    private String phone;
    private String address;
    private String cep;
}
