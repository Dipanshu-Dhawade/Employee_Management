package com.management.Employee_Management.service.impl;

import com.management.Employee_Management.dto.request.RoleRequestDto;
import com.management.Employee_Management.dto.responce.RoleResponseDto;
import com.management.Employee_Management.model.Role;
import com.management.Employee_Management.repository.RoleRepository;
import com.management.Employee_Management.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private  final RoleRepository roleRepository;
    @Override
    public RoleResponseDto createRole(RoleRequestDto request) {
        boolean b = roleRepository.existsByRoleType(request.getRoleType().toUpperCase());
        if(b) throw  new RuntimeException("Role is already present "+request.getRoleType());
        Role role = new Role();
        role.setRoleType(request.getRoleType().toUpperCase());
        Role saverole = roleRepository.save(role);
        return new RoleResponseDto(saverole.getId(),saverole.getRoleType());
    }

    @Override
    public RoleResponseDto getRoleById(Long id) {

        Role  role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Role not found with id: " + id)
                );

        return new RoleResponseDto(role.getId(),role.getRoleType());
    }
    @Override
    public List<RoleResponseDto> getAllRoles() {

        return roleRepository.findAll()
                .stream()
                .map(role -> new RoleResponseDto(
                        role.getId(),
                        role.getRoleType()
                ))
                .toList();
    }

    @Override
    public RoleResponseDto updateRole(Long id, RoleRequestDto request) {
        boolean b = roleRepository.existsByRoleType(request.getRoleType().toUpperCase());
        if(b) throw  new RuntimeException("Role is already present "+request.getRoleType());

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

        role.setRoleType(request.getRoleType().toUpperCase());
        Role  updatedRole = roleRepository.save(role);
        return new RoleResponseDto(
                updatedRole.getId(),
                updatedRole.getRoleType()
        );
    }

    @Override
    public void permentlydeleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        roleRepository.delete(role);
    }

    @Transactional
    @Override
    public List<RoleResponseDto> createMultipleRole(List<RoleRequestDto> dtos) {

        List<Role> roles = new ArrayList<>();
        for (var dto  : dtos){
            Role role = new Role();
            boolean b = roleRepository.existsByRoleType(dto.getRoleType());
            if(b) throw  new RuntimeException("This Role name is already exits "+dto.getRoleType());
            role.setRoleType(dto.getRoleType());
            roles.add(role);
        }
        List<Role> saveroles = roleRepository.saveAll(roles);

        List<RoleResponseDto> responcedto = new ArrayList<>();
        for (var saverole  : saveroles){
            RoleResponseDto responce = new RoleResponseDto();
            responce.setRoleType(saverole.getRoleType());
            responcedto.add(responce);
        }
        return responcedto;
    }


}
