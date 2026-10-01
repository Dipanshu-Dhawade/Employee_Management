package com.management.Employee_Management.controller;
import com.management.Employee_Management.dto.request.EmployeeRequestDto;
import com.management.Employee_Management.dto.responce.EmployeeResponseDto;
import com.management.Employee_Management.security.CheckingRequest;
import com.management.Employee_Management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CheckingRequest checkingRequest;


   // ADD EMPLOYEE
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_EMPLOYEE')")
    public ResponseEntity<EmployeeResponseDto> addEmployee(
            @RequestBody EmployeeRequestDto request) {

        EmployeeResponseDto response = employeeService.addEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // GET EMPLOYEE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(
            @PathVariable Long id, Authentication authentication) {
        checkingRequest.checkAuthority(authentication,"READ_EMPLOYEE");
        checkingRequest.check(authentication,id);

        EmployeeResponseDto response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(response);
    }


    // GET ALL EMPLOYEES
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        List<EmployeeResponseDto> response = employeeService.getAllEmployees();
        return ResponseEntity.ok(response);
    }


    // UPDATE EMPLOYEE
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeRequestDto request,
            Authentication authentication) {
        checkingRequest.checkAuthority(authentication,"UPDATE_EMPLOYEE");
        checkingRequest.check(authentication,id);

        EmployeeResponseDto response = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(response);
    }


    // PATCH EMPLOYEE
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> patchEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeRequestDto request,
            Authentication authentication ) {
        checkingRequest.checkAuthority(authentication,"UPDATE_EMPLOYEE");
        checkingRequest.check(authentication,id);

        EmployeeResponseDto response =
                employeeService.patchEmployee(id, request);

        return ResponseEntity.ok(response);
    }

    // SOFT DELETE EMPLOYEE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_EMPLOYEE')")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.ok("Employee deleted successfully");
    }


    // RESTORE EMPLOYEE
    @PatchMapping("/{id}/restore")
    @PreAuthorize("hasAuthority('DELETE_EMPLOYEE')")
    public ResponseEntity<String> restoreEmployee(
            @PathVariable Long id,
            Authentication authentication) {

        employeeService.restoreEmployee(id);
        return ResponseEntity.ok("Employee restored successfully");
    }

    // SOFT DELETE EMPLOYEE
    @DeleteMapping("/{id}/permently")
    @PreAuthorize("hasAuthority('DELETE_EMPLOYEE')")
    public ResponseEntity<String> permentlydeleteEmployee(
            @PathVariable Long id,
            Authentication authentication) {
        checkingRequest.checkAuthority(authentication,"DELETE_EMPLOYEE");
        checkingRequest.check(authentication,id);

        employeeService.PermetelyDelete(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

}