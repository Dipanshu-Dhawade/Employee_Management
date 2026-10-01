package com.management.Employee_Management.dto.request;


import com.management.Employee_Management.enums.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeavesRequestDto {

    private Long employeeId;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String leaveType;

    private Long  hrId;
}