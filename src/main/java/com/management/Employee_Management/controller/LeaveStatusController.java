package com.management.Employee_Management.controller;

import com.management.Employee_Management.dto.request.LeaveStatusRequestDto;
import com.management.Employee_Management.dto.responce.LeaveStatusResponceDto;
import com.management.Employee_Management.service.LeaveStatusService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-status")
@RequiredArgsConstructor
public class LeaveStatusController {
    private final LeaveStatusService leaveStatusService;
    // CREATE
    @PostMapping
    @PreAuthorize("hasAuthority('CREAT_STATUS')")
    public ResponseEntity<LeaveStatusResponceDto> createStatus(@RequestBody LeaveStatusRequestDto request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(leaveStatusService.createStatus(request));
    }

    // GET ALL
    @GetMapping
    @PreAuthorize("hasAuthority('READ_STATUS')")
    public ResponseEntity<List<LeaveStatusResponceDto>> getAllStatuses() {

        return ResponseEntity.ok(
                leaveStatusService.getAllStatuses()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_STATUS')")
    public ResponseEntity<LeaveStatusResponceDto> getStatusById(@PathVariable Long id) {
        return ResponseEntity.ok(leaveStatusService.getStatusById(id));
    }

    // UPDATE
    @PutMapping()
    @PreAuthorize("hasAuthority('UPDATE_STATUS')")
    public ResponseEntity<LeaveStatusResponceDto> updateStatus(@RequestBody LeaveStatusRequestDto
                                                                       request) {
        return ResponseEntity.ok(leaveStatusService.updateStatus(request));
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_STATUS')")
    public ResponseEntity<Void> deleteStatus(
            @PathVariable Long id) {
        leaveStatusService.deleteStatus(id);
        return ResponseEntity.noContent().build();
    }
}