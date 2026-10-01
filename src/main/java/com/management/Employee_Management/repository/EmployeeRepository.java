package com.management.Employee_Management.repository;

import com.management.Employee_Management.model.Employee;
import com.management.Employee_Management.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByPhone(String username);
    Employee findByDesignation_DesignationId(Long designationid);

    Employee findByDepartment_departmentId(Long deptid);
}