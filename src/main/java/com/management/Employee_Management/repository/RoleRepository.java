package com.management.Employee_Management.repository;

import com.management.Employee_Management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByRoleType(String roleType);

    Role findByRoleType(String users);
}