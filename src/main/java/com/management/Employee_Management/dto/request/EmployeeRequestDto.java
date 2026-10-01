package com.management.Employee_Management.dto.request;

import com.management.Employee_Management.enums.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDto {

    private String employee_code;

    private String first_name;

    private String last_name;

    private String phone;

    private LocalDate date_of_birth;

    private LocalDate date_of_joining;

    private EmployeeStatus status;

    private Long user_id;

    private Long department_id;

    private Long designation_id;
}