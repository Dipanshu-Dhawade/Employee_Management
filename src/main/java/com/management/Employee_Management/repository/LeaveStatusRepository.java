package com.management.Employee_Management.repository;

import com.management.Employee_Management.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveStatusRepository extends JpaRepository<LeaveStatus, Long> {
    LeaveStatus findByStatusName(String pending);
}