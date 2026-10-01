package com.management.Employee_Management.service;

import com.management.Employee_Management.dto.request.ChangeLeaveStatusRequestDto;
import com.management.Employee_Management.dto.request.LeavesRequestDto;
import com.management.Employee_Management.dto.responce.LeavesResponseDto;
import com.management.Employee_Management.model.Leave;

import java.util.List;

public interface LeavesService {

    // Apply for leave
    LeavesResponseDto applyLeave(LeavesRequestDto leave);

    // Get leave by ID
    LeavesResponseDto getLeaveById(Long leaveId);

    // Get all leaves
    List<LeavesResponseDto> getAllLeaves();

    // Get leaves of a particular employee
    List<LeavesResponseDto> getLeavesByEmployee(Long employeeId);

    // Delete leave
    void deleteLeave(Long leaveId);

    LeavesResponseDto changingLeaveStatus(ChangeLeaveStatusRequestDto dto);

    public List<LeavesResponseDto> getAllLeaveByLeaveStatusId(Long statusId);

    List<LeavesResponseDto> getAllLeaveByLeaveType(String leaveType);
}
