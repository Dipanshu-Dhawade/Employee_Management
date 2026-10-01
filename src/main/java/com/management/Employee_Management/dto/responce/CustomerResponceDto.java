package com.management.Employee_Management.dto.responce;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponceDto {

    private Long customerId;

    private String name;

    private String email;

    private String phoneNumber;
}
