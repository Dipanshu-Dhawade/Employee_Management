package com.management.Employee_Management.controller;


import com.management.Employee_Management.model.Users;
import com.management.Employee_Management.security.CheckingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import com.management.Employee_Management.dto.request.EmployeeEducationRequestDto;
import com.management.Employee_Management.dto.responce.EmployeeEducationResponseDto;
import com.management.Employee_Management.service.EmployeeEducationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-education")
@RequiredArgsConstructor
public class EmployeeEducationController {

    private final EmployeeEducationService employeeEducationService;
    private final CheckingRequest checkingRequest;


    // ADD EDUCATION
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_EMPLOYEE_EDUCATION')")
    public ResponseEntity<EmployeeEducationResponseDto> addEducation(@RequestBody EmployeeEducationRequestDto request) {
//        checkingRequest.checkAuthority(authentication, "DELETE_EMPLOYEE_DOCUMENT");
//        Long userId = checkingRequest.getUserIdFormDocId(id);
//        checkingRequest.check(authentication, userId);
        EmployeeEducationResponseDto response = employeeEducationService.addEducation(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // GET EDUCATION BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEducationResponseDto> getEducationById(@PathVariable Long id,
                                                                         Authentication authentication ) {
        if(!checkingRequest.RoleISAdmin(authentication)) {
            checkingRequest.checkAuthority(authentication, "READ_EMPLOYEE_EDUCATION");
            Long userId = checkingRequest.getUserIdFormEduId(id);
            checkingRequest.check(authentication, userId);
        }
        EmployeeEducationResponseDto response = employeeEducationService.getEducationById(id);

        return ResponseEntity.ok(response);
    }


    // GET ALL EDUCATIONS
    @GetMapping
    public ResponseEntity<List<EmployeeEducationResponseDto>> getAllEducations() {

        List<EmployeeEducationResponseDto> response = employeeEducationService.getAllEducations();
        return ResponseEntity.ok(response);
    }


    // UPDATE EDUCATION
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeEducationResponseDto> updateEducation(@PathVariable Long id, @RequestBody EmployeeEducationRequestDto request,
                                                                        Authentication authentication) {

        checkingRequest.checkAuthority(authentication, "UPDATE_EMPLOYEE_EDUCATION");
        Long userId = checkingRequest.getUserIdFormEduId(id);
        checkingRequest.check(authentication, userId);

        EmployeeEducationResponseDto response = employeeEducationService.updateEducation(id, request);
        return ResponseEntity.ok(response);
    }


    // PATCH EDUCATION
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeEducationResponseDto> patchEducation(@PathVariable Long id, @RequestBody EmployeeEducationRequestDto request,
                                                                       Authentication authentication) {
        checkingRequest.checkAuthority(authentication, "UPDATE_EMPLOYEE_EDUCATION");
        Long userId = checkingRequest.getUserIdFormEduId(id);
        checkingRequest.check(authentication, userId);

        EmployeeEducationResponseDto response = employeeEducationService.patchEducation(id, request);
        return ResponseEntity.ok(response);
    }


    // SOFT DELETE EDUCATION
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEducation(@PathVariable Long id,Authentication authentication) {
        checkingRequest.checkAuthority(authentication, "DELETE_EMPLOYEE_EDUCATION");
        Long userId = checkingRequest.getUserIdFormEduId(id);
        checkingRequest.check(authentication, userId);

        employeeEducationService.deleteEducation(id);
        return ResponseEntity.ok("Education deleted successfully");
    }


    // RESTORE EDUCATION
    @PatchMapping("/{id}/restore")
    public ResponseEntity<EmployeeEducationResponseDto> restoreEducation(@PathVariable Long id ,
                                                                         Authentication authentication) {

        checkingRequest.checkAuthority(authentication, "UPDATE_EMPLOYEE_EDUCATION");
        Long userId = checkingRequest.getUserIdFormEduId(id);
        checkingRequest.check(authentication, userId);

        EmployeeEducationResponseDto response =
                employeeEducationService.restoreEducation(id);

        return ResponseEntity.ok(response);
    }
}