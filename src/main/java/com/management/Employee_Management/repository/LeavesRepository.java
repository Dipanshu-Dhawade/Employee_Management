package com.management.Employee_Management.repository;

import com.management.Employee_Management.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface LeavesRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployee_EmployeeId(Long employeeId);

    @Query("""
    SELECT l
    FROM Leave l
    WHERE LOWER(l.leaveType) LIKE LOWER(CONCAT(:leaveType, '%'))
""")
    List<Leave> findByLeaveTypeStartingWithIgnoreCase(String leaveType);
}