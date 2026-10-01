package com.management.Employee_Management.service.impl;

import com.management.Employee_Management.dto.request.EmployeeExperienceRequestDto;
import com.management.Employee_Management.dto.responce.EmployeeExperienceResponseDto;
import com.management.Employee_Management.model.Employee;
import com.management.Employee_Management.model.EmployeeExperience;
import com.management.Employee_Management.repository.EmployeeExperienceRepository;
import com.management.Employee_Management.repository.EmployeeRepository;
import com.management.Employee_Management.service.EmployeeExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Employee_ExprienceServiceImpl implements EmployeeExperienceService {

    private final EmployeeExperienceRepository empExperienceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeExperienceResponseDto addExperience(
            EmployeeExperienceRequestDto request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId()).
                orElseThrow(() -> new RuntimeException("Employee is not create" + request.getEmployeeId()));
        EmployeeExperience experience = new EmployeeExperience();

        experience.setCompanyName(request.getCompany_name());
        experience.setStartDate(request.getStart_date());
        experience.setEndDate(request.getEnd_date());
        experience.setCurrentlyWorking(request.getCurrently_working());
        experience.setEmployee(employee);
        EmployeeExperience saved =
                empExperienceRepository.save(experience);

        return mapToResponse(saved);
    }

    @Override
    public EmployeeExperienceResponseDto getExperienceById(Long id) {

        EmployeeExperience experience =
                empExperienceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Experience not found with id: " + id
                                )
                        );

        if (experience.isDeleted()) {
            throw new RuntimeException(
                    "Experience not found with id: " + id
            );
        }

        return mapToResponse(experience);
    }

    @Override
    public List<EmployeeExperienceResponseDto> getAllExperiences() {

        return empExperienceRepository.findAll()
                .stream()
                .filter(experience -> !experience.isDeleted())
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public EmployeeExperienceResponseDto updateExperience(
            Long id,
            EmployeeExperienceRequestDto request) {

        EmployeeExperience experience =
                empExperienceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Experience not found with id: " + id
                                )
                        );

        if (experience.isDeleted()) {
            throw new RuntimeException(
                    "Experience not found with id: " + id
            );
        }

        experience.setCompanyName(request.getCompany_name());
        experience.setStartDate(request.getStart_date());
        experience.setEndDate(request.getEnd_date());
        experience.setCurrentlyWorking(request.getCurrently_working());

        EmployeeExperience saved =
                empExperienceRepository.save(experience);

        return mapToResponse(saved);
    }

    @Override
    public EmployeeExperienceResponseDto patchExperience(
            Long id,
            EmployeeExperienceRequestDto request) {

        EmployeeExperience experience =
                empExperienceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Experience not found with id: " + id
                                )
                        );

        if (experience.isDeleted()) {
            throw new RuntimeException(
                    "Experience not found with id: " + id
            );
        }

        if (request.getCompany_name() != null) {
            experience.setCompanyName(request.getCompany_name());
        }

        if (request.getStart_date() != null) {
            experience.setStartDate(request.getStart_date());
        }

        if (request.getEnd_date() != null) {
            experience.setEndDate(request.getEnd_date());
        }

        if (request.getCurrently_working() != null) {
            experience.setCurrentlyWorking(
                    request.getCurrently_working()
            );
        }

        EmployeeExperience saved =
                empExperienceRepository.save(experience);

        return mapToResponse(saved);
    }

    @Override
    public void deleteExperience(Long id) {

        EmployeeExperience experience =
                empExperienceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Experience not found with id: " + id
                                )
                        );

        experience.setDeleted(true);

        empExperienceRepository.save(experience);
    }

    @Override
    public void restoreExperience(Long id) {

        EmployeeExperience experience =
                empExperienceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Experience not found with id: " + id
                                )
                        );

        experience.setDeleted(false);

        empExperienceRepository.save(experience);
    }

    private EmployeeExperienceResponseDto mapToResponse(
            EmployeeExperience experience) {

        return new EmployeeExperienceResponseDto(
                experience.getExprienceId(),
                experience.getCompanyName(),
                experience.getStartDate(),
                experience.getEndDate(),
                experience.getCurrentlyWorking()
        );
    }
}