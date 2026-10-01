package com.management.Employee_Management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEducationRequestDto {

    private String qualification;

    private String specialization;

    private String institution_name;

    private String university_name;

    private LocalDate passing_year;

    private Double percentage;

    private String grade;

    private Long employeeId;

}
