package com.management.Employee_Management.controller;

import com.management.Employee_Management.dto.request.EmployeeExperienceRequestDto;
import com.management.Employee_Management.dto.responce.EmployeeExperienceResponseDto;
import com.management.Employee_Management.dto.responce.EmployeeResponseDto;
import com.management.Employee_Management.security.CheckingRequest;
import com.management.Employee_Management.service.EmployeeExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-experience")
@RequiredArgsConstructor
public class EmployeeExperienceController {

    private final EmployeeExperienceService employeeExperienceService;
    private  final CheckingRequest checkingRequest;


    // ADD EXPERIENCE
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_EMPLOYEE_EXPERIENCE')")
    public ResponseEntity<EmployeeExperienceResponseDto> addExperience(@RequestBody EmployeeExperienceRequestDto request) {

        EmployeeExperienceResponseDto response = employeeExperienceService.addExperience(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET EXPERIENCE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeExperienceResponseDto> getExperienceById(@PathVariable Long id,
                                                                           Authentication authentication) {

        checkingRequest.checkAuthority(authentication, "READ_EMPLOYEE_EXPERIENCE");
        Long userId = checkingRequest.getUserIdFormExpId(id);
        checkingRequest.check(authentication, userId);

        EmployeeExperienceResponseDto response = employeeExperienceService.getExperienceById(id);
        return ResponseEntity.ok(response);
    }


    // GET ALL EXPERIENCES
    @GetMapping
    public ResponseEntity<List<EmployeeExperienceResponseDto>> getAllExperiences() {

        List<EmployeeExperienceResponseDto> response = employeeExperienceService.getAllExperiences();
        return ResponseEntity.ok(response);
    }


    // UPDATE EXPERIENCE
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeExperienceResponseDto> updateExperience(@PathVariable Long id, @RequestBody EmployeeExperienceRequestDto request,
                                                                          Authentication authentication) {
        if(!checkingRequest.RoleISAdmin(authentication)) {
            checkingRequest.checkAuthority(authentication, "UPDATE_EMPLOYEE_EXPERIENCE");
            Long userId = checkingRequest.getUserIdFormExpId(id);
            checkingRequest.check(authentication, userId);
        }
        EmployeeExperienceResponseDto response = employeeExperienceService.updateExperience(id, request);
        return ResponseEntity.ok(response);
    }


    // ==========================================
    // PATCH EXPERIENCE
    // ==========================================

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeExperienceResponseDto> patchExperience(
            @PathVariable Long id,
            @RequestBody EmployeeExperienceRequestDto request,
            Authentication authentication) {

        checkingRequest.checkAuthority(authentication, "UPDATE_EMPLOYEE_EXPERIENCE");
        Long userId = checkingRequest.getUserIdFormExpId(id);
        checkingRequest.check(authentication, userId);

        EmployeeExperienceResponseDto response =
                employeeExperienceService.patchExperience(
                        id,
                        request
                );

        return ResponseEntity.ok(response);
    }

   // SOFT DELETE EXPERIENCE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExperience(@PathVariable Long id,
                                                   Authentication authentication) {
        checkingRequest.checkAuthority(authentication, "DELETE_EMPLOYEE_EXPERIENCE");
        Long userId = checkingRequest.getUserIdFormExpId(id);
        checkingRequest.check(authentication, userId);
        employeeExperienceService.deleteExperience(id);
        return ResponseEntity.ok("Experience deleted successfully");
    }


    // RESTORE EXPERIENCE
    @PatchMapping("/{id}/restore")
    public ResponseEntity<String> restoreExperience(@PathVariable Long id , Authentication authentication) {

        checkingRequest.checkAuthority(authentication, "DELETE_EMPLOYEE_EXPERIENCE");
        Long userId = checkingRequest.getUserIdFormExpId(id);
        checkingRequest.check(authentication, userId);

        employeeExperienceService.restoreExperience(id);
        return ResponseEntity.ok("Experience restored successfully");
    }
}