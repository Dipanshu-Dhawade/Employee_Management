package com.management.Employee_Management.service;

import com.management.Employee_Management.dto.request.PermissionRequestDto;
import com.management.Employee_Management.dto.request.RolePermissionMultiRequestDto;
import com.management.Employee_Management.dto.request.RolePermissionRequestDto;
import com.management.Employee_Management.dto.responce.*;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface RolePermissionService {

    void assignPermission(RolePermissionRequestDto permissionRequestDto);
    public List<RolePermissionResponceDtos> assignMultiplePermission(List<RolePermissionMultiRequestDto> requests);
    void removePermission(Long id );
    public void updatePermission(RolePermissionMultiRequestDto rolePermissionMultiRequestDto);
    //List<RolePermissionResponceDto> getPermissionsByRole(Long roleId);
    public List<ShowPermissionbyRoleId> getPermissionsByRole(Long roleId);
    public List<ShowRolebypermissionId> getRolePermissionsByPermission(Long permissionId);

    void  assingAllPermissionForAdmin(Long adminId);

    public List<ShowPermissionbyRoleDtos> getAllRolePermission();
}