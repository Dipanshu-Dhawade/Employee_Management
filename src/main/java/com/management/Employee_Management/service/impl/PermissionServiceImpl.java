package com.management.Employee_Management.service.impl;

import com.management.Employee_Management.dto.request.PermissionRequestDto;
import com.management.Employee_Management.dto.responce.PermissionResponseDto;
import com.management.Employee_Management.model.Permission;
import com.management.Employee_Management.repository.PermissionRepository;
import com.management.Employee_Management.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public PermissionResponseDto createPermission(
            PermissionRequestDto request) {

        if (permissionRepository.existsByPermission(request.getPermission())) {
            throw new RuntimeException("Permission already exists: " + request.getPermission());
        }

        Permission permission = new Permission();

        permission.setPermission(request.getPermission());
        permission.setDescription(request.getDescription());

        Permission saved = permissionRepository.save(permission);
        return new PermissionResponseDto(
                saved.getPermissionId(),
                saved.getPermission(),
                saved.getDescription()
        );
    }

    @Override
    public PermissionResponseDto getPermissionById(Long id) {

        Permission permission =
                permissionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Permission not found with id: " + id
                                )
                        );

        return new PermissionResponseDto(
                permission.getPermissionId(),
                permission.getPermission(),
                permission.getDescription()
        );
    }

    @Override
    public List<PermissionResponseDto> createMultiplePermissions(
            List<PermissionRequestDto> requests) {

        return requests.stream()
                .map(request -> {

                    if (permissionRepository
                            .existsByPermission(request.getPermission())) {

                        throw new RuntimeException(
                                "Permission already exists: "
                                        + request.getPermission()
                        );
                    }

                    Permission permission = new Permission();

                    permission.setPermission(request.getPermission());
                    permission.setDescription(request.getDescription());

                    Permission saved = permissionRepository.save(permission);

                    return new PermissionResponseDto(
                            saved.getPermissionId(),
                            saved.getPermission(),
                            saved.getDescription()
                    );
                })
                .toList();
    }

    @Override
    public List<PermissionResponseDto> getAllPermissions() {

        return permissionRepository.findAll()
                .stream()
                .map(permission ->
                        new PermissionResponseDto(
                                permission.getPermissionId(),
                                permission.getPermission(),
                                permission.getDescription()
                        )
                )
                .toList();
    }

    @Override
    public PermissionResponseDto updatePermission(
            Long id,
            PermissionRequestDto request) {

        Permission permission =
                permissionRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Permission not found with id: " + id));

        permission.setPermission(request.getPermission());
        permission.setDescription(request.getDescription());

        Permission updated =
                permissionRepository.save(permission);

        return new PermissionResponseDto(
                updated.getPermissionId(),
                updated.getPermission(),
                updated.getDescription()
        );
    }

    @Override
    public void deletePermission(Long id) {

        Permission permission =
                permissionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Permission not found with id: " + id
                                )
                        );

        permissionRepository.delete(permission);
    }

    @Override
    public void deleteAllPermission() {
     permissionRepository.deleteAll();
    }
}