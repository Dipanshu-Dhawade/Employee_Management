package com.management.Employee_Management.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private Long userId;
    private String username;
    private String role;
    private String token;
    private String refreshToken;
}