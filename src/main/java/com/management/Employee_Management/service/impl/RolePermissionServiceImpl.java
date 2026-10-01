package com.management.Employee_Management.service.impl;

import com.management.Employee_Management.dto.request.PermissionRequestDto;
import com.management.Employee_Management.dto.request.RolePermissionMultiRequestDto;
import com.management.Employee_Management.dto.request.RolePermissionRequestDto;
import com.management.Employee_Management.dto.responce.*;
import com.management.Employee_Management.model.*;
import com.management.Employee_Management.repository.PermissionRepository;
import com.management.Employee_Management.repository.RoleRepository;
import com.management.Employee_Management.repository.Role_PermissionRepository;
import com.management.Employee_Management.repository.UsersRepository;
import com.management.Employee_Management.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl implements RolePermissionService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final Role_PermissionRepository rolePermissionRepository;
    private final UsersRepository usersRepository;


    @Override
    public void assignPermission(RolePermissionRequestDto permissionRequestDto) {

        Role role = roleRepository.findById(permissionRequestDto.getRoleId())
                .orElseThrow(() ->
                        new RuntimeException("Role not found with id: " + permissionRequestDto.getRoleId()));

        Permission permission = permissionRepository.findById(permissionRequestDto.getPermissionId())
                .orElseThrow(() -> new RuntimeException("Permission not found with id: " + permissionRequestDto.getPermissionId()));

        boolean alreadyExists = rolePermissionRepository.existsByRole_IdAndPermission_PermissionId(
                permissionRequestDto.getRoleId(),
                permissionRequestDto.getPermissionId()
        );

        if (alreadyExists) {
            throw new RuntimeException("Permission is already assigned to this role");
        }
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);
        rolePermissionRepository.save(rolePermission);

    }

    @Override
    public List<RolePermissionResponceDtos> assignMultiplePermission(List<RolePermissionMultiRequestDto> requests) {

        List<RolePermission> rolePermissions = new ArrayList<>();
        for (RolePermissionMultiRequestDto request : requests) {

            Role role = roleRepository.findById(request.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found: " + request.getRoleId()));

            for (Long permissionId : request.getPermissionId()) {

                Permission permission = permissionRepository.findById(permissionId)
                        .orElseThrow(() -> new RuntimeException("Permission not found: " + permissionId));

                RolePermission rolePermission = new RolePermission();

                rolePermission.setRole(role);
                rolePermission.setPermission(permission);

                rolePermissions.add(rolePermission);
            }
          }

        List<RolePermission> savedRolePermissions =
                rolePermissionRepository.saveAll(rolePermissions);

        List<RolePermissionResponceDtos> responseList = new ArrayList<>();

        for (RolePermission rolePermission : savedRolePermissions) {
            RolePermissionResponceDtos dto = new RolePermissionResponceDtos();
            dto.setId(rolePermission.getId());
            dto.setPermission(rolePermission.getPermission().getPermission());
            dto.setRole(rolePermission.getRole().getRoleType());
            dto.setDescription(rolePermission.getPermission().getDescription());
            responseList.add(dto);
        }

        return responseList;
    }

    @Override
    public void removePermission(Long id) {
        RolePermission rolePermission = rolePermissionRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Role Permission is Not Found " + id));
        rolePermissionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updatePermission(RolePermissionMultiRequestDto rolePermissionMultiRequestDto) {
        Role role = roleRepository.findById(rolePermissionMultiRequestDto.getRoleId())
                .orElseThrow(() ->
                        new RuntimeException("Role not found: " + rolePermissionMultiRequestDto.getRoleId()));
        // Remove existing permissions
        List<RolePermission> existingPermissions =
                rolePermissionRepository.findByRoleId(rolePermissionMultiRequestDto.getRoleId());

        rolePermissionRepository.deleteAll(existingPermissions);

        rolePermissionRepository.flush();

        List<RolePermission> rolePermissions = new ArrayList<>();
        for (var permissionId : rolePermissionMultiRequestDto.getPermissionId()) {
            Permission permission = permissionRepository.findById(permissionId)
                    .orElseThrow(() -> new RuntimeException("Permission not found: " + permissionId));
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRole(role);
            rolePermission.setPermission(permission);
            rolePermissions.add(rolePermission);
        }
        rolePermissionRepository.saveAll(rolePermissions);
    }


    @Override
    public List<ShowPermissionbyRoleId> getPermissionsByRole(Long roleId) {

        // First check role exists
        roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        return rolePermissionRepository
                .findByRole_Id(roleId)
                .stream()
                .map(rolePermission -> {
                    Permission permission = rolePermission.getPermission();
                    return new ShowPermissionbyRoleId(permission.getPermission());
                })
                .toList();
    }

    @Override
    public List<ShowRolebypermissionId> getRolePermissionsByPermission(Long permissionId) {

        permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found with id: " + permissionId));

        List<RolePermission> rolePermissions =
                rolePermissionRepository.findByPermission_PermissionId(permissionId);
        List<ShowRolebypermissionId> response = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissions) {
            Role role = rolePermission.getRole();
            ShowRolebypermissionId dto = new ShowRolebypermissionId(role.getRoleType());
            response.add(dto);
        }

        return response;
    }


    @Override
    @Transactional
    public void assingAllPermissionForAdmin(Long adminId) {
        Users users = usersRepository.findById(adminId).
                orElseThrow(() -> new RuntimeException("Uesr Is Not Found " + adminId));
        boolean admin = users.getRole().getRoleType().equals("ADMIN");
        String roleType = users.getRole().getRoleType();

        System.out.println("Role Type = [" + roleType + "]");

        if (!"ADMIN".equalsIgnoreCase(roleType)) {
            throw new RuntimeException(
                    "This is not admin. Actual role: " + roleType
            );
        }
        if(!admin) throw  new RuntimeException("this is not admin");

        rolePermissionRepository.deleteByRoleId(users.getRole().getId());

        List<Permission> allpermission = permissionRepository.findAll();
        if(allpermission==null) throw  new RuntimeException("Permission are empty ");

        Role role = roleRepository.findById(users.getRole().getId()).
                orElseThrow(() -> new RuntimeException("This Role is not create"));
        List<RolePermission> rolePermissions = new ArrayList<>();
        for (var permission : allpermission) {
            RolePermission rolePermission= new RolePermission();
            rolePermission.setPermission(permission);
            rolePermission.setRole(role);
            rolePermissions.add(rolePermission);
        }
        rolePermissionRepository.saveAll(rolePermissions);
    }

    @Override
    public List<ShowPermissionbyRoleDtos> getAllRolePermission() {
        List<RolePermission> allRolePermission = rolePermissionRepository.findAll();
        List<ShowPermissionbyRoleDtos> dtos =  new ArrayList<>();

          for (var singleobj  :  allRolePermission){
              ShowPermissionbyRoleDtos showPermissionbyRoleId = new ShowPermissionbyRoleDtos();
              showPermissionbyRoleId.setId(singleobj.getId());
              showPermissionbyRoleId.setRoleType(singleobj.getRole().getRoleType());
              showPermissionbyRoleId.setPermissiotype(singleobj.getPermission().getPermission());
             dtos.add(showPermissionbyRoleId);
           }
           return dtos;
    }
}
