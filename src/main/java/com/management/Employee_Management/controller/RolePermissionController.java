package com.management.Employee_Management.controller;

import com.management.Employee_Management.dto.request.RolePermissionMultiRequestDto;
import com.management.Employee_Management.dto.request.RolePermissionRequestDto;
import com.management.Employee_Management.dto.responce.*;
import com.management.Employee_Management.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-permissions")
@RequiredArgsConstructor
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    // Assign one permission to a role
    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('CREATE_ROLE_PERMISSION')")
    public ResponseEntity<String> assignPermission(
            @RequestBody RolePermissionRequestDto request) {

        rolePermissionService.assignPermission(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Permission assigned successfully");
    }

    @PostMapping("/assingALLPermissionToAdmin/{adminId}")
   // @PreAuthorize("hasAuthority('CREATE_ROLE_PERMISSION')")
    public ResponseEntity<String> assingAllPermissionForAdmin(@PathVariable Long adminId) {
        rolePermissionService.assingAllPermissionForAdmin(adminId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Added Successfully");
    }

    // Assign multiple permissions
    @PostMapping("/assign-multiple")
    @PreAuthorize("hasAuthority('CREATE_ROLE_PERMISSION')")
    public ResponseEntity<List<RolePermissionResponceDtos>> assignMultiplePermission(
            @RequestBody List<RolePermissionMultiRequestDto> requests) {

        List<RolePermissionResponceDtos> response = rolePermissionService.assignMultiplePermission(requests);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Remove permission from role
    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('DELETE_ROLE_PERMISSION')")
    public ResponseEntity<String> removePermission(@PathVariable Long   id ) {

        rolePermissionService.removePermission(id);

        return ResponseEntity.ok("Permission removed successfully");
    }

    // Update all permissions of a role
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('UPDATE_ROLE_PERMISSION')")
    public ResponseEntity<String> updatePermission(@RequestBody RolePermissionMultiRequestDto request) {
        rolePermissionService.updatePermission(request);
        return ResponseEntity.ok("Permissions updated successfully");
    }

    // Get permissions assigned to a role
    @GetMapping("/role/{roleId}")
    @PreAuthorize("hasAuthority('READ_ROLE_PERMISSION')")
    public ResponseEntity<List<ShowPermissionbyRoleId>> getPermissionsByRole(
            @PathVariable Long roleId) {

        return ResponseEntity.ok(
                rolePermissionService.getPermissionsByRole(roleId)
        );
    }

    @GetMapping("/getAllRolePermission")
    public ResponseEntity<List<ShowPermissionbyRoleDtos>> getAllRolePermission() {
        return ResponseEntity.ok(rolePermissionService.getAllRolePermission());
    }


    // Get roles assigned to a permission
    @GetMapping("/permission/{permissionId}")
    @PreAuthorize("hasAuthority('READ_ROLE_PERMISSION')")
    public ResponseEntity<List<ShowRolebypermissionId>> getRolePermissionsByPermission(
            @PathVariable Long permissionId) {

        return ResponseEntity.ok(
                rolePermissionService.getRolePermissionsByPermission(permissionId)
        );
    }
}