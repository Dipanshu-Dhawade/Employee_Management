package com.management.Employee_Management.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto {

    private String name;

    private String email;

    private String phoneNumber;

    private Long  userId;
}