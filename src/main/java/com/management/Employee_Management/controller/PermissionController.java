package com.management.Employee_Management.controller;

import com.management.Employee_Management.dto.request.PermissionRequestDto;
import com.management.Employee_Management.dto.responce.PermissionResponseDto;
import com.management.Employee_Management.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
   // @PreAuthorize("hasAuthority('CREATE_PERMISSION')")
    public ResponseEntity<PermissionResponseDto> createPermission(
            @RequestBody PermissionRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(permissionService.createPermission(request));
    }

    @PostMapping("/multiple")
    //@PreAuthorize("hasAuthority('CREATE_PERMISSION')")
    public ResponseEntity<List<PermissionResponseDto>> createMultiplePermissions(
            @RequestBody List<PermissionRequestDto> requests) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(permissionService.createMultiplePermissions(requests));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_PERMISSION')")
    public ResponseEntity<PermissionResponseDto> getPermissionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                permissionService.getPermissionById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<PermissionResponseDto>> getAllPermissions() {

        return ResponseEntity.ok(
                permissionService.getAllPermissions()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PERMISSION')")
    public ResponseEntity<PermissionResponseDto> updatePermission(
            @PathVariable Long id,
            @RequestBody PermissionRequestDto request) {

        return ResponseEntity.ok(
                permissionService.updatePermission(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_PERMISSION')")
    public ResponseEntity<String> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok("Permission deleted successfully");
    }

    public ResponseEntity<String> deleteAllPermission() {
        permissionService.deleteAllPermission();
        return ResponseEntity.ok("delete all data");
    }

}