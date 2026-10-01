package com.management.Employee_Management.service;

import com.management.Employee_Management.dto.request.EmployeeRequestDto;
import com.management.Employee_Management.dto.responce.EmployeeResponseDto;

import java.util.List;

public interface EmployeeService {

    // CREATE
    EmployeeResponseDto addEmployee(EmployeeRequestDto request);

    // READ
    EmployeeResponseDto getEmployeeById(Long id);

    List<EmployeeResponseDto> getAllEmployees();

    // UPDATE
    EmployeeResponseDto updateEmployee(
            Long id,
            EmployeeRequestDto request);

    // PATCH
    EmployeeResponseDto patchEmployee(
            Long id,
            EmployeeRequestDto request);

    // SOFT DELETE
    void deleteEmployee(Long id);

    // RESTORE
    void restoreEmployee(Long id);

    public void PermetelyDelete(Long id);
}