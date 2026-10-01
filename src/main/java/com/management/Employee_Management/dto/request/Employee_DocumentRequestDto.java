package com.management.Employee_Management.dto.request;

import com.management.Employee_Management.model.Employee;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Employee_DocumentRequestDto {

        private String document_type;
        private String file_name;
        private String file_url;
        private LocalDate uploaded_at;
        private Long employeeId;
    }
