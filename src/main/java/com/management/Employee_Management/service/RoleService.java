package com.management.Employee_Management.service;

import com.management.Employee_Management.dto.request.RoleRequestDto;
import com.management.Employee_Management.dto.responce.RoleResponseDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface RoleService {

    RoleResponseDto createRole(RoleRequestDto request);

    RoleResponseDto getRoleById(Long id);

    List<RoleResponseDto> getAllRoles();

    RoleResponseDto updateRole(Long id, RoleRequestDto request);

    void permentlydeleteRole(Long id);

    List<RoleResponseDto> createMultipleRole(List<RoleRequestDto> request);


}
