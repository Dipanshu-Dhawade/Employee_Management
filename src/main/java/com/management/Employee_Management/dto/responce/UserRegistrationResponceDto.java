package com.management.Employee_Management.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationResponceDto {

    private  Long  id;
    private String first_name;
    private String jwt;

}
