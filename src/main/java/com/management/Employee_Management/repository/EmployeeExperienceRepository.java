package com.management.Employee_Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.Employee_Management.model.EmployeeExperience;

public interface EmployeeExperienceRepository extends JpaRepository<EmployeeExperience, Long> {
}