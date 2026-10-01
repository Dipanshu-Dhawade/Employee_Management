package com.management.Employee_Management.dto.responce;
import com.management.Employee_Management.enums.LeaveType;
import com.management.Employee_Management.model.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeavesResponseDto {

    private Long leaveId;

    private Long employeeId;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String leaveType;

    private LeaveStatus status;

    private LocalDateTime appliedAt;

    private Long approvedBy;
}
