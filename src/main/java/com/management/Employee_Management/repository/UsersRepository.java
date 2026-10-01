package com.management.Employee_Management.repository;

import com.management.Employee_Management.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String username);

    Users findByUsername(String username);
}