package com.benitomiyazato.userservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponse {

    private UUID userId;
    private String email;
    private String fullName;
    private String cpf;
    private String phone;
    private String address;
    private String cep;
}
