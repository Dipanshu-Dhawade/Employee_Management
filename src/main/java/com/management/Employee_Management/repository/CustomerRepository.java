package com.management.Employee_Management.repository;

import com.management.Employee_Management.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}