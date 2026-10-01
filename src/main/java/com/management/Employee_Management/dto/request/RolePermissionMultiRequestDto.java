package com.management.Employee_Management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionMultiRequestDto {

    private Long roleId;
    private List<Long>  permissionId;
}
