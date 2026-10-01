package com.management.Employee_Management.service.impl;

import com.management.Employee_Management.dto.request.ChangeLeaveStatusRequestDto;
import com.management.Employee_Management.dto.request.LeavesRequestDto;
import com.management.Employee_Management.dto.responce.LeavesResponseDto;
import com.management.Employee_Management.model.*;
import com.management.Employee_Management.repository.*;
import com.management.Employee_Management.service.LeavesService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeavesServiceImpl implements LeavesService {

    private final LeavesRepository leavesRepository;
    private final EmployeeRepository employeeRepository;
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final LeaveStatusRepository leaveStatusRepository;


    // APPLY LEAVE
    @Override
    public LeavesResponseDto applyLeave(LeavesRequestDto request) {

        if (request.getEmployeeId() == null) {
            throw new RuntimeException("Employee ID is required");
        }

        Users users = usersRepository.findById(request.getHrId()).get();
        Long roleid = users.getRole().getId();
        Role role = roleRepository.findById(roleid).get();
        if (!role.getRoleType().equals("HR")) throw new RuntimeException("This id is not hr");

        Employee employee = employeeRepository
                .findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found: " + request.getEmployeeId()));
        Leave leave = new Leave();
        leave.setEmployee(employee);
        leave.setFromDate(request.getFromDate());
        leave.setToDate(request.getToDate());
        leave.setLeaveType(request.getLeaveType());
        leave.setApprovedBy(users);

        LeaveStatus pending = leaveStatusRepository.findByStatusName("PENDING");

        // New leave always starts as PENDING
        leave.setStatus(pending);

        leave.setAppliedAt(LocalDateTime.now());

        Leave savedLeave = leavesRepository.save(leave);

        return mapToResponse(savedLeave);
    }

    // GET LEAVE BY ID
    @Override
    @Transactional(readOnly = true)
    public LeavesResponseDto getLeaveById(Long leaveId) {

        Leave leave = leavesRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found: " + leaveId));
        return mapToResponse(leave);
    }


    // GET ALL LEAVES
    @Override
    @Transactional(readOnly = true)
    public List<LeavesResponseDto> getAllLeaves() {

        return leavesRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET LEAVES BY EMPLOYEE
    @Override
    @Transactional(readOnly = true)
    public List<LeavesResponseDto> getLeavesByEmployee(
            Long employeeId) {

        // Check employee exists
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

        return leavesRepository
                .findByEmployee_EmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // DELETE LEAVE
    @Override
    public void deleteLeave(Long leaveId) {

        Leave leave = leavesRepository.findById(leaveId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Leave not found: " + leaveId
                        )
                );

        leavesRepository.delete(leave);
    }

    @Override
    public LeavesResponseDto changingLeaveStatus(ChangeLeaveStatusRequestDto dto) {
        Leave leave = leavesRepository.findById(dto.getLeaveId())
                .orElseThrow(() -> new RuntimeException("Leave not found: " + dto.getLeaveId()));

        //user is hr only
        Users users = usersRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("THis is Not Hr "));
        Long roleid = users.getRole().getId();
        Role role = roleRepository.findById(roleid).
                orElseThrow(() -> new RuntimeException("This User Doest Not have role Id "));
        if (!role.getRoleType().equals("HR")) throw new RuntimeException("This id is not hr");

        LeaveStatus leaveStatus = leaveStatusRepository.findById(dto.getStatusId()).
                orElseThrow(() -> new RuntimeException("You id is not "));

        leave.setStatus(leaveStatus);
        leave.setApprovedBy(users);

        Leave updatedLeave = leavesRepository.save(leave);

        return mapToResponse(updatedLeave);
    }

    @Override
    public List<LeavesResponseDto> getAllLeaveByLeaveStatusId(Long statusId) {
        List<Leave> allLeave = leavesRepository.findAll();

        List<LeavesResponseDto> list = allLeave.stream().filter(s -> s.getStatus().getId() == statusId).map(this::mapToResponse).toList();
         return  list;
    }

    @Override
    public List<LeavesResponseDto> getAllLeaveByLeaveType(String leaveType) {
        List<LeavesResponseDto> list = leavesRepository.findByLeaveTypeStartingWithIgnoreCase(leaveType).stream().
                map(this::mapToResponse).toList();
    return  list;
     }


    // ENTITY → RESPONSE DTO
    private LeavesResponseDto mapToResponse(Leave leave) {

        LeavesResponseDto response = new LeavesResponseDto();

        response.setLeaveId(leave.getLeaveId());

        response.setEmployeeId(
                leave.getEmployee().getEmployeeId()
        );

        response.setFromDate(leave.getFromDate());
        response.setToDate(leave.getToDate());
        response.setLeaveType(leave.getLeaveType());
        response.setStatus(leave.getStatus());
        response.setAppliedAt(LocalDateTime.now());

        if (leave.getApprovedBy() != null) {

            response.setApprovedBy(
                    leave.getApprovedBy().getUser_id()
            );
        }

        return response;
    }
}