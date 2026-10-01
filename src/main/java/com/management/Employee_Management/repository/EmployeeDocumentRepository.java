package com.management.Employee_Management.repository;

import com.management.Employee_Management.model.EmployeeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, Long> {
    EmployeeDocument findByFileName(String fileName);
}