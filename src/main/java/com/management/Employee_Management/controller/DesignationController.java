package com.management.Employee_Management.controller;

import com.management.Employee_Management.repository.DesignationRepository;
import com.management.Employee_Management.security.CheckingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import com.management.Employee_Management.dto.request.DesignationRequestDto;
import com.management.Employee_Management.dto.responce.DesignationResponceDto;
import com.management.Employee_Management.service.DesignationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/designations")
@RequiredArgsConstructor
public class DesignationController {

    private final DesignationService designationService;
    private  final CheckingRequest checkingRequest;


    // CREATE
    @PostMapping
//    @PreAuthorize("hasAuthority('CREATE_DESIGNATION')")
    public ResponseEntity<DesignationResponceDto> addDesignation(@RequestBody
                                                                     DesignationRequestDto designationRequestDto) {

        DesignationResponceDto response = designationService.addDesignation(designationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //user can access if they have role
    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DesignationResponceDto> getDesignationById(
            @PathVariable Long id,
            Authentication authentication) {
        if(!checkingRequest.RoleISAdmin(authentication)) {
            checkingRequest.checkAuthority(authentication, "READ_DESIGNATION");
            Long userId = checkingRequest.getUserIdFormDesignationId(id);
            checkingRequest.check(authentication, userId);
        }
        DesignationResponceDto response = designationService.getDesignationById(id);
        return ResponseEntity.ok(response);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<DesignationResponceDto>> getAllDesignations() {

        List<DesignationResponceDto> response = designationService.getAllDesignations();
        return ResponseEntity.ok(response);
    }

    // FULL UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_DESIGNATION')")
    public ResponseEntity<DesignationResponceDto> updateDesignation(
            @PathVariable Long id,
            @RequestBody DesignationRequestDto designationRequestDto) {

        DesignationResponceDto response = designationService.updateDesignation(id, designationRequestDto);
        return ResponseEntity.ok(response);
    }

    // PARTIAL UPDATE
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_DESIGNATION')")
    public ResponseEntity<DesignationResponceDto> partialUpdatedDesignation(
            @PathVariable Long id,
            @RequestBody DesignationRequestDto designationRequestDto) {
        DesignationResponceDto response = designationService.patchDesignation(id,designationRequestDto);
        return ResponseEntity.ok(response);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_DESIGNATION')")
    public ResponseEntity<String> deleteDesignation(@PathVariable Long id) {
        designationService.deleteDesignation(id);
        return ResponseEntity.ok("Designation deleted successfully");
    }

    // PERMANENT DELETE
    @DeleteMapping("/permanent/{id}")
    @PreAuthorize("hasAuthority('DELETE_DESIGNATION')")
    public ResponseEntity<String> permanentlyDeleteDesignation(@PathVariable Long id) {
        designationService.permanentlyDeleteDesignation(id);
        return ResponseEntity.ok("Designation permanently deleted successfully");
    }
}