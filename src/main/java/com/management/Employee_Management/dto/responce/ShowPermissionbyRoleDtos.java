package com.management.Employee_Management.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowPermissionbyRoleDtos {
    private Long  id;
    private String roleType;
    private String permissiotype;
}
