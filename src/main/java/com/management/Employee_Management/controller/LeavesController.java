package com.management.Employee_Management.controller;

import com.management.Employee_Management.dto.request.ChangeLeaveStatusRequestDto;
import com.management.Employee_Management.dto.request.LeavesRequestDto;
import com.management.Employee_Management.dto.responce.LeavesResponseDto;
import com.management.Employee_Management.security.CheckingRequest;
import com.management.Employee_Management.service.LeavesService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeavesController {

    private final LeavesService leavesService;
    private final CheckingRequest checkingRequest;


    // APPLY LEAVE
    @PostMapping
    @PreAuthorize("hasAuthority('APPLY_LEAVE')")
    public ResponseEntity<LeavesResponseDto> applyLeave(@RequestBody LeavesRequestDto request) {
        LeavesResponseDto response = leavesService.applyLeave(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // GET LEAVE BY ID
    @GetMapping("/{leaveId}")
    @PreAuthorize("hasAuthority('READ_LEAVE')")
    public ResponseEntity<LeavesResponseDto> getLeaveById(@PathVariable Long leaveId) {
        LeavesResponseDto response = leavesService.getLeaveById(leaveId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("GetAllLeaveByLeaveStatus")
    @PreAuthorize("hasAuthority('READ_LEAVE')")
    public ResponseEntity<List<LeavesResponseDto>> GetAllLeaveByLeaveStatusId(@PathParam("statusId") Long statusId) {
        List<LeavesResponseDto> response = leavesService.getAllLeaveByLeaveStatusId(statusId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("GetAllLeaveByLeaveType")
    @PreAuthorize("hasAuthority('READ_LEAVE')")
    public ResponseEntity<List<LeavesResponseDto>> GetAllLeaveByLeaveType(@PathParam("LeaveType") String LeaveType) {
        List<LeavesResponseDto> response = leavesService.getAllLeaveByLeaveType(LeaveType);
        return ResponseEntity.ok(response);
    }


    // GET ALL LEAVES
    @GetMapping
    @PreAuthorize("hasAuthority('READ_LEAVE')")
    public ResponseEntity<List<LeavesResponseDto>> getAllLeaves() {
        List<LeavesResponseDto> response = leavesService.getAllLeaves();
        return ResponseEntity.ok(response);
    }


    // GET LEAVES BY EMPLOYEE
    @GetMapping("/employee")
    public ResponseEntity<List<LeavesResponseDto>> getLeavesByEmployee(
            @PathParam("employeeId") Long employeeId, Authentication authentication) {

        if (!(checkingRequest.RoleISAdmin(authentication) || checkingRequest.RoleisHr(authentication))) {
            checkingRequest.checkAuthority(authentication,"READ_LEAVE");
            Long userid = checkingRequest.getUserIdfromEmployeeId(employeeId);
            checkingRequest.check(authentication,userid);
          }
        List<LeavesResponseDto> response = leavesService.getLeavesByEmployee(employeeId);
        return ResponseEntity.ok(response);
    }

    // APPROVE LEAVE
    @PutMapping("/changingStatus")
    @PreAuthorize("hasAuthority('UPDATE_LEAVE')")
    public ResponseEntity<LeavesResponseDto> changingLeaveStatus(@RequestBody ChangeLeaveStatusRequestDto dto) {
        LeavesResponseDto response = leavesService.changingLeaveStatus(dto);
        return ResponseEntity.ok(response);
    }

    // DELETE LEAVE
    @DeleteMapping("/{leaveId}")
    @PreAuthorize("hasAuthority('DELETE_LEAVE')")
    public ResponseEntity<Void> deleteLeave(@PathVariable("leaveId") Long leaveId) {
        leavesService.deleteLeave(leaveId);
        return ResponseEntity.noContent().build();
    }
}
