package com.management.Employee_Management.service.impl;

import com.management.Employee_Management.dto.request.DepartmentRequestDto;
import com.management.Employee_Management.dto.responce.DepartmentResponceDto;
import com.management.Employee_Management.model.Department;
import com.management.Employee_Management.repository.DepartmentRepository;
import com.management.Employee_Management.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentResponceDto addDepartment(DepartmentRequestDto departmentRequestDto) {

        Department department = new Department();
        department.setDepartmentName(departmentRequestDto.getDepartmentName());
        department.setDescription(departmentRequestDto.getDescription());

        Department savedepartment = departmentRepository.save(department);
        return new DepartmentResponceDto(savedepartment.getDepartmentName(),
                savedepartment.getDescription());

    }


    @Override
    public DepartmentResponceDto updateDepartment(Long id, DepartmentRequestDto departmentRequestDto) {
        Department department = departmentRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Department is not Found" + id));

        department.setDepartmentName(departmentRequestDto.getDepartmentName());
        department.setDescription(departmentRequestDto.getDescription());
        Department saveDepartment = departmentRepository.save(department);

        return new DepartmentResponceDto(saveDepartment.getDepartmentName(),
                saveDepartment.getDescription());
    }

    @Override
    public DepartmentResponceDto patchDepartment(
            Long id,
            DepartmentRequestDto departmentRequestDto) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Department is Not Found: " + id));

        if (departmentRequestDto.getDepartmentName() != null) {
            department.setDepartmentName(
                    departmentRequestDto.getDepartmentName()
            );
        }

        if (departmentRequestDto.getDescription() != null) {
            department.setDescription(
                    departmentRequestDto.getDescription()
            );
        }

        Department savedDepartment = departmentRepository.save(department);

        return new DepartmentResponceDto(
                savedDepartment.getDepartmentName(),
                savedDepartment.getDescription()
        );
    }

    @Override
    public DepartmentResponceDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Department is Not Found" + id));
        return new DepartmentResponceDto(department.getDepartmentName(), department.getDescription());
    }

    @Override
    public List<DepartmentResponceDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponceDto> dtos = new ArrayList<>();

        for (var department : departments) {
            DepartmentResponceDto dto = new DepartmentResponceDto();
            dto.setDepartmentName(department.getDepartmentName());
            dto.setDescription(department.getDescription());
            dtos.add(dto);
        }
        return dtos;
    }
    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Dpartment is not Found" + id));
        department.setDeleted(true);

    }

    @Override
    public void permanentlyDeleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Department is Not Found " + id));
        departmentRepository.deleteById(id);
    }
}
