package com.management.Employee_Management.service;

import com.management.Employee_Management.dto.request.EmployeeEducationRequestDto;
import com.management.Employee_Management.dto.responce.EmployeeEducationResponseDto;

import java.util.List;

public interface EmployeeEducationService {

    // CREATE
    public EmployeeEducationResponseDto addEducation(EmployeeEducationRequestDto employeeEducationRequestDto) ;

    // READ
    public EmployeeEducationResponseDto getEducationById(Long id);

    public List<EmployeeEducationResponseDto> getAllEducations();

    // UPDATE
    public EmployeeEducationResponseDto updateEducation(Long id, EmployeeEducationRequestDto education);
    // PATCH
    public EmployeeEducationResponseDto patchEducation(Long id, EmployeeEducationRequestDto education);

    // SOFT DELETE
    public void deleteEducation(Long id);

    // RESTORE
    public EmployeeEducationResponseDto restoreEducation(Long id) ;
}