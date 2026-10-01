package com.management.Employee_Management.service;

import com.management.Employee_Management.dto.request.EmployeeExperienceRequestDto;
import com.management.Employee_Management.dto.responce.EmployeeExperienceResponseDto;

import java.util.List;

public interface EmployeeExperienceService {

    // CREATE
    EmployeeExperienceResponseDto addExperience(
            EmployeeExperienceRequestDto request);

    // READ
    EmployeeExperienceResponseDto getExperienceById(Long id);

    List<EmployeeExperienceResponseDto> getAllExperiences();

    // UPDATE
    EmployeeExperienceResponseDto updateExperience(
            Long id,
            EmployeeExperienceRequestDto request);

    // PATCH
    EmployeeExperienceResponseDto patchExperience(
            Long id,
            EmployeeExperienceRequestDto request);

    // SOFT DELETE
    void deleteExperience(Long id);

    // RESTORE
    void restoreExperience(Long id);
}