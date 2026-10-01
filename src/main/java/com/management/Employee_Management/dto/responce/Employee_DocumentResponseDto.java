package com.management.Employee_Management.dto.responce;

import com.management.Employee_Management.model.EmployeeDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee_DocumentResponseDto {
    private String document_type;
    private String file_name;
    private String file_url;
    private LocalDate uploaded_at;
}
