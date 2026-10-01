package com.management.Employee_Management.service;

import com.management.Employee_Management.dto.request.DesignationRequestDto;
import com.management.Employee_Management.dto.responce.DesignationResponceDto;

import java.util.List;

public interface DesignationService {

    // CREATE
    DesignationResponceDto addDesignation(DesignationRequestDto designationRequestDto);

    // READ
    DesignationResponceDto getDesignationById(Long id);

    List<DesignationResponceDto> getAllDesignations();

    // UPDATE
    DesignationResponceDto updateDesignation(Long id, DesignationRequestDto designation);

    // PATCH
    DesignationResponceDto patchDesignation(Long id, DesignationRequestDto designation);

    // SOFT DELETE
    void deleteDesignation(Long id);

    // RESTORE
    void permanentlyDeleteDesignation(Long id);
}