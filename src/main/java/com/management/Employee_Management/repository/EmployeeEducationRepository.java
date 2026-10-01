package com.management.Employee_Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.Employee_Management.model.EmployeeEducation;

public interface EmployeeEducationRepository extends JpaRepository<EmployeeEducation, Long> {
}