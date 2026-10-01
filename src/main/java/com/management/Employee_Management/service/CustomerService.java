package com.management.Employee_Management.service;

import com.management.Employee_Management.dto.request.CustomerRequestDto;
import com.management.Employee_Management.dto.responce.CustomerResponceDto;

import java.util.List;

public interface CustomerService {

    CustomerResponceDto createCustomer(CustomerRequestDto customer);
    CustomerResponceDto getCustomerById(Long customerId);
    List<CustomerResponceDto> getAllCustomers();
    CustomerResponceDto updateCustomer(Long customerId, CustomerRequestDto customer);
    void deleteCustomer(Long customerId);
}