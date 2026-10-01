package com.management.Employee_Management.controller;

import com.management.Employee_Management.dto.request.CustomerRequestDto;
import com.management.Employee_Management.dto.responce.CustomerResponceDto;
import com.management.Employee_Management.security.CheckingRequest;
import com.management.Employee_Management.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CheckingRequest checkingRequest;

    // CREATE
    @PostMapping
    public ResponseEntity<CustomerResponceDto> createCustomer(
            @RequestBody CustomerRequestDto customer, Authentication authentication) {

        checkingRequest.checkAuthority(authentication, "add Customer");
        checkingRequest.check(authentication, customer.getUserId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerService.createCustomer(customer));
    }

    // GET BY ID
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponceDto> getCustomerById(
            @PathVariable Long customerId, Authentication authentication) {

        if (!checkingRequest.RoleISAdmin(authentication)) {
            checkingRequest.checkAuthority(authentication, "Read_Customer");
            Long userid = checkingRequest.getUserIdFormCustomerId(customerId);
            checkingRequest.check(authentication, userid);
        }
        return ResponseEntity.ok(
                customerService.getCustomerById(customerId)
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<CustomerResponceDto>> getAllCustomers() {

        return ResponseEntity.ok(
                customerService.getAllCustomers()
        );
    }

    // UPDATE
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponceDto> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody CustomerRequestDto customer,
            Authentication authentication) {
        checkingRequest.checkAuthority(authentication, "Upadtating Customer");
        checkingRequest.check(authentication, customer.getUserId());
        return ResponseEntity.ok(
                customerService.updateCustomer(customerId, customer)
        );
    }

    // DELETE
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Long customerId,
            Authentication authentication) {
        if (!checkingRequest.RoleISAdmin(authentication)) {
            checkingRequest.checkAuthority(authentication, "Deleting  Customer");
            Long userid = checkingRequest.getUserIdFormCustomerId(customerId);
            checkingRequest.check(authentication, userid);
        }
        customerService.deleteCustomer(customerId);

        return ResponseEntity.noContent().build();
    }
}