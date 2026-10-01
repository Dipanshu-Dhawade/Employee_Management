package com.management.Employee_Management.repository;

import com.management.Employee_Management.model.Department;
import com.management.Employee_Management.model.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DesignationRepository
        extends JpaRepository<Designation, Long> {

    List<Designation> findByDeletedFalse();

    Optional<Designation> findBydesignationIdAndDeletedFalse(Long id);

    Designation findByDesignationName(String designationName);
}