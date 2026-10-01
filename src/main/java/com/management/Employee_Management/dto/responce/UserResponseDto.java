package com.management.Employee_Management.dto.responce;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long user_id;

    private String username;
    private String email;
    private LocalDate update_at;
}
