package com.benitomiyazato.userservice.dto;

import com.benitomiyazato.userservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthResponse {
    private String email;
    private String encodedPassword;
    private Role role;
}
