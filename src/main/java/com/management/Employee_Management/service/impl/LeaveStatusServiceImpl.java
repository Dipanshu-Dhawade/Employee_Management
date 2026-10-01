package com.management.Employee_Management.service.impl;

import com.management.Employee_Management.dto.request.LeaveStatusRequestDto;
import com.management.Employee_Management.dto.responce.LeaveStatusResponceDto;
import com.management.Employee_Management.model.LeaveStatus;
import com.management.Employee_Management.repository.LeaveStatusRepository;
import com.management.Employee_Management.service.LeaveStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class LeaveStatusServiceImpl implements LeaveStatusService {

    private final LeaveStatusRepository leaveStatusRepository;

    @Override
    public LeaveStatusResponceDto createStatus(
            LeaveStatusRequestDto request) {

        LeaveStatus status = new LeaveStatus();
        status.setStatusName(request.getStatusName());
        LeaveStatus savedStatus = leaveStatusRepository.save(status);
        return mapToResponse(savedStatus);
    }

    @Override
    public List<LeaveStatusResponceDto> getAllStatuses() {

        return leaveStatusRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public LeaveStatusResponceDto getStatusById(Long id) {

        LeaveStatus status = leaveStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave status not found: " + id));
        return mapToResponse(status);
    }

    @Override
    public LeaveStatusResponceDto updateStatus(LeaveStatusRequestDto request) {

        LeaveStatus status = leaveStatusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new RuntimeException("Leave status not found: " + request.getStatusId()));

        status.setStatusName(request.getStatusName());
        LeaveStatus updatedStatus = leaveStatusRepository.save(status);
        return mapToResponse(updatedStatus);
    }

    @Override
    public void deleteStatus(Long id) {

        LeaveStatus status = leaveStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave status not found: " + id));
        leaveStatusRepository.delete(status);
    }

    // Entity → Response DTO
    private LeaveStatusResponceDto mapToResponse(
            LeaveStatus status) {

        LeaveStatusResponceDto response = new LeaveStatusResponceDto();
        response.setId(status.getId());
        response.setStatusName(status.getStatusName());
        return response;
    }
}
