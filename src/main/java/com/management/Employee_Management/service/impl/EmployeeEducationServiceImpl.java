package com.management.Employee_Management.service.impl;

import com.management.Employee_Management.dto.request.EmployeeEducationRequestDto;
import com.management.Employee_Management.dto.request.EmployeeRequestDto;
import com.management.Employee_Management.dto.responce.EmployeeEducationResponseDto;
import com.management.Employee_Management.model.Employee;
import com.management.Employee_Management.model.EmployeeEducation;
import com.management.Employee_Management.repository.EmployeeRepository;
import com.management.Employee_Management.service.EmployeeEducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import com.management.Employee_Management.repository.EmployeeEducationRepository;

@Service
@RequiredArgsConstructor
public class EmployeeEducationServiceImpl implements EmployeeEducationService {

    private final EmployeeEducationRepository empEduRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeEducationResponseDto addEducation(EmployeeEducationRequestDto requestDto) {
        Employee employee = employeeRepository.findById(requestDto.getEmployeeId()).
                orElseThrow(() -> new RuntimeException(" No Employye is Found " + requestDto.getEmployeeId()));

        EmployeeEducation emp = new EmployeeEducation();
        emp.setQualification(requestDto.getQualification());
        emp.setSpecialization(requestDto.getSpecialization());
        emp.setInstitutionName(requestDto.getInstitution_name());
        emp.setUniversityName(requestDto.getUniversity_name());
        emp.setPassingYear(requestDto.getPassing_year());
        emp.setPercentage(requestDto.getPercentage());
        emp.setGrade(requestDto.getGrade());
        emp.setEmployee(employee);

        EmployeeEducation save = empEduRepository.save(emp);
        return new EmployeeEducationResponseDto(save.getEducationId(), save.getQualification());
    }

    @Override
    public EmployeeEducationResponseDto getEducationById(Long id) {
        EmployeeEducation employeeEducation = empEduRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Education is Not Found " + id));
        if (employeeEducation.isDeleted()) throw new RuntimeException("Education is in history but deleted " + id);
        return new EmployeeEducationResponseDto(employeeEducation.getEducationId(),
                employeeEducation.getQualification());
    }

    @Override
    public List<EmployeeEducationResponseDto> getAllEducations() {
        List<EmployeeEducation> allsducation = empEduRepository.findAll();
        return allsducation.stream().
                map(s -> new EmployeeEducationResponseDto(
                        s.getEducationId(),
                        s.getQualification()))
                .toList();

    }

    @Override
    public EmployeeEducationResponseDto updateEducation(
            Long id,
            EmployeeEducationRequestDto education) {

        EmployeeEducation emp = empEduRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Education not found with id: " + id));

        emp.setQualification(education.getQualification());
        emp.setSpecialization(education.getSpecialization());
        emp.setInstitutionName(education.getInstitution_name());
        emp.setUniversityName(education.getUniversity_name());
        emp.setPassingYear(education.getPassing_year());
        emp.setPercentage(education.getPercentage());
        emp.setGrade(education.getGrade());

        EmployeeEducation save = empEduRepository.save(emp);

        return new EmployeeEducationResponseDto(
                save.getEducationId(),
                save.getQualification()
        );
    }

    @Override
    public EmployeeEducationResponseDto patchEducation(
            Long id,
            EmployeeEducationRequestDto education) {

        EmployeeEducation emp = empEduRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Education not found with id: " + id)
                );

        if (education.getQualification() != null) {
            emp.setQualification(education.getQualification());
        }

        if (education.getSpecialization() != null) {
            emp.setSpecialization(education.getSpecialization());
        }

        if (education.getInstitution_name() != null) {
            emp.setInstitutionName(education.getInstitution_name());
        }

        if (education.getUniversity_name() != null) {
            emp.setUniversityName(education.getUniversity_name());
        }

        if (education.getPassing_year() != null) {
            emp.setPassingYear(education.getPassing_year());
        }

        if (education.getPercentage() != null) {
            emp.setPercentage(education.getPercentage());
        }

        if (education.getGrade() != null) {
            emp.setGrade(education.getGrade());
        }

        EmployeeEducation save = empEduRepository.save(emp);

        return new EmployeeEducationResponseDto(
                save.getEducationId(),
                save.getQualification()
        );
    }

    @Override
    public void deleteEducation(Long id) {
        EmployeeEducation emp = empEduRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));
        empEduRepository.deleteById(id);
        empEduRepository.save(emp);
    }

    @Override
    public EmployeeEducationResponseDto restoreEducation(Long id) {

        EmployeeEducation emp = empEduRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));
        emp.setDeleted(false);
        EmployeeEducation save = empEduRepository.save(emp);
        return new EmployeeEducationResponseDto(
                save.getEducationId(),
                save.getQualification()
        );
    }
}
