package com.management.Employee_Management.dto.responce;

import com.management.Employee_Management.enums.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto {

    private Long employee_id;

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
