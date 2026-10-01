package com.management.Employee_Management.dto.request;

import com.management.Employee_Management.enums.EmployeeStatus;
import com.management.Employee_Management.model.EmployeeDocument;
import com.management.Employee_Management.model.EmployeeEducation;
import com.management.Employee_Management.model.EmployeeExperience;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequestDto {
    private String username;

    private String password;

    private String email;

    private LocalDate create_at;

    private LocalDate update_at;

    private String employee_code;

    private String first_name;

    private String last_name;

    private String phone;

    private LocalDate date_of_birth;

    private LocalDate date_of_joining;

    private EmployeeStatus status;

    private Long departmentId;

    private Long designationId;

    private List<EmployeeEducationRequestDto> employeeEducations;

    private List<EmployeeExperienceRequestDto> employeeExperiences;

    private List<Employee_DocumentRequestDto> employeeDocuments;


}



