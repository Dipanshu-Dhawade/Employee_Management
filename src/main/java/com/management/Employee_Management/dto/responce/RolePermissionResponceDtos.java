package com.management.Employee_Management.dto.responce;

import com.management.Employee_Management.model.Permission;
import com.management.Employee_Management.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionResponceDtos {
    private  Long id ;
    private String role;
    private String permission;
    private String description;
}
