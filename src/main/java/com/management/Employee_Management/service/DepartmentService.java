package com.management.Employee_Management.service;


import com.management.Employee_Management.dto.request.DepartmentRequestDto;
import com.management.Employee_Management.dto.responce.DepartmentResponceDto;

import java.util.List;

public interface DepartmentService {

    // CREATE
    public DepartmentResponceDto addDepartment(DepartmentRequestDto department);

    // UPDATE - full update
    public DepartmentResponceDto updateDepartment(Long id, DepartmentRequestDto department);

    // PATCH - partial update
    public DepartmentResponceDto patchDepartment(Long id, DepartmentRequestDto department);

    // READ - single
    public DepartmentResponceDto getDepartmentById(Long id);

    // READ - all active departments
    public List<DepartmentResponceDto> getAllDepartments();

    // SOFT DELETE
    public void deleteDepartment(Long id);

    // Hard DELETE
    public void permanentlyDeleteDepartment(Long id);
}
