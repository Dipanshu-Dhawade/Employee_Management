package com.management.Employee_Management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveStatusRequestDto {
    private  Long  statusId;
    private String statusName;
}
