package com.management.Employee_Management.repository;

import com.management.Employee_Management.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    boolean existsByPermission(String permission);
}