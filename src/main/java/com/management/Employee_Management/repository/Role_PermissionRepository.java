package com.management.Employee_Management.repository;

import com.management.Employee_Management.model.Permission;
import com.management.Employee_Management.model.RolePermission;
import com.management.Employee_Management.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface Role_PermissionRepository extends JpaRepository<RolePermission, Long> {
  boolean existsByRole_IdAndPermission_PermissionId(
          Long roleId,
          Long permissionId
  );

  List<RolePermission> findByRole_Id(Long roleId);
  List<RolePermission> findByRoleId(Long roleId);
  Permission findByPermission(Long permissionId);
  List<RolePermission> findByPermission_PermissionId(Long permissionId);

  @Query("""
        SELECT rp.permission
        FROM RolePermission rp
        WHERE rp.role.id = :roleId
    """)
  List<Permission> findAllPermissionByRoleId(
          @Param("roleId") Long roleId
  );

  @Modifying
  @Query("""
    DELETE FROM RolePermission rp
    WHERE rp.role.id = :roleId
""")
  void deleteByRoleId(@Param("roleId") Long roleId);
}