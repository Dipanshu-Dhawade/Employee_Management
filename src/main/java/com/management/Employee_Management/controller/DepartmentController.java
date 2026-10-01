package com.management.Employee_Management.controller;
import com.management.Employee_Management.security.CheckingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;


import com.management.Employee_Management.dto.request.DepartmentRequestDto;
import com.management.Employee_Management.dto.responce.DepartmentResponceDto;
import com.management.Employee_Management.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private  final CheckingRequest checkingRequest;

    // CREATE
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_DEPARTMENT')")
    public ResponseEntity<DepartmentResponceDto> addDepartment(
            @RequestBody DepartmentRequestDto departmentRequestDto) {

        DepartmentResponceDto response = departmentService.addDepartment(departmentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // UPDATE - Full Update
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_DEPARTMENT')")
    public ResponseEntity<DepartmentResponceDto> updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentRequestDto departmentRequestDto) {

        DepartmentResponceDto response = departmentService.updateDepartment(id, departmentRequestDto);
        return ResponseEntity.ok(response);
    }

    // PATCH - Partial Update
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_DEPARTMENT')")
    public ResponseEntity<DepartmentResponceDto> patchDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentRequestDto departmentRequestDto) {

        DepartmentResponceDto response = departmentService.patchDepartment(id, departmentRequestDto);
        return ResponseEntity.ok(response);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponceDto> getDepartmentById(
            @PathVariable Long id,
            Authentication authentication) {
        if(!checkingRequest.RoleISAdmin(authentication)) {
            checkingRequest.checkAuthority(authentication, "READ_DEPARTMENT");
            Long userId = checkingRequest.getUserIdFormDeptId(id);
            checkingRequest.check(authentication, userId);
        }
        DepartmentResponceDto response = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(response);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<DepartmentResponceDto>> getAllDepartments() {
        List<DepartmentResponceDto> response = departmentService.getAllDepartments();
        return ResponseEntity.ok(response);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_DEPARTMENT')")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Department deleted successfully");
    }

    // PERMANENT DELETE
    @DeleteMapping("/permanent/{id}")
    @PreAuthorize("hasAuthority('DELETE_DEPARTMENT')")
    public ResponseEntity<String> permanentlyDeleteDepartment(@PathVariable Long id) {
        departmentService.permanentlyDeleteDepartment(id);
        return ResponseEntity.ok("Department permanently deleted successfully");
    }
}
