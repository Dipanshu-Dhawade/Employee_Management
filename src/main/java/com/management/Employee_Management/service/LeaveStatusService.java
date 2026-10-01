package com.management.Employee_Management.service;

import com.management.Employee_Management.dto.request.LeaveStatusRequestDto;
import com.management.Employee_Management.dto.responce.LeaveStatusResponceDto;

import java.util.List;

public interface LeaveStatusService {

    LeaveStatusResponceDto createStatus(LeaveStatusRequestDto leaveStatus);

    List<LeaveStatusResponceDto> getAllStatuses();

    LeaveStatusResponceDto getStatusById(Long id);

    LeaveStatusResponceDto updateStatus(LeaveStatusRequestDto leaveStatus);

    void deleteStatus(Long id);
}