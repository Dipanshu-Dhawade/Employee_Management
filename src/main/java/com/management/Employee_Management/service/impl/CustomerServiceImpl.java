package com.management.Employee_Management.service.impl;

import com.management.Employee_Management.dto.request.CustomerRequestDto;
import com.management.Employee_Management.dto.responce.CustomerResponceDto;
import com.management.Employee_Management.model.Customer;
import com.management.Employee_Management.model.Role;
import com.management.Employee_Management.model.Users;
import com.management.Employee_Management.repository.CustomerRepository;
import com.management.Employee_Management.repository.RoleRepository;
import com.management.Employee_Management.repository.UsersRepository;
import com.management.Employee_Management.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UsersRepository usersRepository;
    private  final RoleRepository roleRepository;

    @Override
    public CustomerResponceDto createCustomer(CustomerRequestDto customer) {

        Users users = usersRepository.findById(customer.getUserId()).get();
        Role role = roleRepository.findByRoleType("CUSTOMER");

        //setting role
        users.setRole(role);
        Customer newCustomer = new Customer();
        newCustomer.setName(customer.getName());
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setPhoneNumber(customer.getPhoneNumber());
        newCustomer.setUsersId(users);
        Customer savedCustomer = customerRepository.save(newCustomer);

        return mapToResponse(savedCustomer);
    }

    @Override
    public CustomerResponceDto getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
        return mapToResponse(customer);
    }

    @Override
    public List<CustomerResponceDto> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CustomerResponceDto updateCustomer(
            Long customerId,
            CustomerRequestDto customer
    ) {

        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found with ID: " + customerId
                        )
                );

        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());

        Customer updatedCustomer =
                customerRepository.save(existingCustomer);

        return mapToResponse(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found with ID: " + customerId
                        )
                );

        customerRepository.delete(customer);
    }

    private CustomerResponceDto mapToResponse(Customer customer) {

        CustomerResponceDto response = new CustomerResponceDto();

        response.setCustomerId(customer.getCustomerId());
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());

        return response;
    }
}