package com.management.Employee_Management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeExperienceRequestDto {

    private String company_name;

    private LocalDate start_date;

    private LocalDate end_date;

    private String currently_working;

    private  Long employeeId;
}