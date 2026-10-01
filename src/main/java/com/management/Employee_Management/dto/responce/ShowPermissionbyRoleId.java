package com.management.Employee_Management.dto.responce;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowPermissionbyRoleId {
    private String permissionType;

}
