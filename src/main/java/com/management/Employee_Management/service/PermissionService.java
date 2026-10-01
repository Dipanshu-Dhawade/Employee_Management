package com.management.Employee_Management.service;

import com.management.Employee_Management.dto.request.PermissionRequestDto;
import com.management.Employee_Management.dto.responce.PermissionResponseDto;

import java.util.List;

public interface PermissionService {

    PermissionResponseDto createPermission(PermissionRequestDto request);

    PermissionResponseDto getPermissionById(Long id);

    List<PermissionResponseDto> createMultiplePermissions(List<PermissionRequestDto> requests);

    List<PermissionResponseDto> getAllPermissions();

    PermissionResponseDto updatePermission(Long id, PermissionRequestDto request);

    void deletePermission(Long id);
    void deleteAllPermission();
}