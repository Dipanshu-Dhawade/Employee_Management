package com.management.Employee_Management.repository;

import com.management.Employee_Management.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByDepartmentName(String designationName);

    Optional<Department> findByDepartmentIdAndDeletedFalse(Long id);
}