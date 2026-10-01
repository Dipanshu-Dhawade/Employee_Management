package com.management.Employee_Management.controller;


import com.management.Employee_Management.dto.request.RoleRequestDto;
import com.management.Employee_Management.dto.responce.RoleResponseDto;
import com.management.Employee_Management.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/saveRole")
//    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public ResponseEntity<RoleResponseDto> createRole(
            @RequestBody RoleRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleService.createRole(request));
    }





    @PostMapping("/assingMultipleRole")
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public ResponseEntity<List<RoleResponseDto>> createMultipleRole(@RequestBody List<RoleRequestDto> request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleService.createMultipleRole(request));
    }



    @GetMapping("/getRole/{id}")
    @PreAuthorize("hasAuthority('READ_ROLE')")
    public ResponseEntity<RoleResponseDto> getRoleById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                roleService.getRoleById(id)
        );
    }

    @GetMapping("/getAllRole")
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {

        return ResponseEntity.ok(
                roleService.getAllRoles()
        );
    }

    @PutMapping("/updateRole/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    public ResponseEntity<RoleResponseDto> updateRole(
            @PathVariable Long id,
            @RequestBody RoleRequestDto request) {

        return ResponseEntity.ok(
                roleService.updateRole(id, request)
        );
    }

    @DeleteMapping("/permentlyDeleteRole/{id}")
   // @PreAuthorize("hasAuthority('DELETE_ROLE')")
    public ResponseEntity<String> deleteRole(
            @PathVariable Long id) {

        roleService.permentlydeleteRole(id);

        return ResponseEntity.ok(
                "Role deleted successfully"
        );
    }
}