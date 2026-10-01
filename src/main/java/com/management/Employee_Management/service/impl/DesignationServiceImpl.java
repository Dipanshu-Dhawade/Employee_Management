package com.management.Employee_Management.service.impl;

import com.management.Employee_Management.dto.request.DesignationRequestDto;
import com.management.Employee_Management.dto.responce.DesignationResponceDto;
import com.management.Employee_Management.model.Designation;
import com.management.Employee_Management.repository.DesignationRepository;
import com.management.Employee_Management.service.DesignationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;


    // CREATE
    @Override
    public DesignationResponceDto addDesignation(
            DesignationRequestDto designationRequestDto) {
        Designation designation = new Designation();
        designation.setDesignationName(designationRequestDto.getDesignationName());
        designation.setDescription(designationRequestDto.getDescription());

        Designation savedDesignation = designationRepository.save(designation);

        return new DesignationResponceDto(
                savedDesignation.getDesignationName(),
                savedDesignation.getDescription()
        );
    }


    // GET BY ID
    @Override
    public DesignationResponceDto getDesignationById(Long id) {

        Designation designation = designationRepository
                .findBydesignationIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Designation is Not Found: " + id));

        return new DesignationResponceDto(
                designation.getDesignationName(),
                designation.getDescription()
        );
    }


    // GET ALL
    @Override
    public List<DesignationResponceDto> getAllDesignations() {

        List<Designation> designations = designationRepository.findByDeletedFalse();

        List<DesignationResponceDto> dtos = new ArrayList<>();

        for (Designation designation : designations) {
            DesignationResponceDto dto = new DesignationResponceDto();
            dto.setDesignationName(designation.getDesignationName());
            dto.setDescription(designation.getDescription());
            dtos.add(dto);
        }

        return dtos;
    }


    // FULL UPDATE
    @Override
    public DesignationResponceDto updateDesignation(Long id, DesignationRequestDto designationRequestDto) {
        Designation designation = designationRepository.findBydesignationIdAndDeletedFalse(id)
                            .orElseThrow(() ->new RuntimeException("Designation is Not Found: " + id));

        designation.setDesignationName(designationRequestDto.getDesignationName());
        designation.setDescription(designationRequestDto.getDescription());
        Designation savedDesignation = designationRepository.save(designation);

        return new DesignationResponceDto(
                savedDesignation.getDesignationName(),
                savedDesignation.getDescription()
        );
    }


    // PATCH
    @Override
    public DesignationResponceDto patchDesignation(Long id, DesignationRequestDto designationRequestDto) {

        Designation designation = designationRepository
                .findBydesignationIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Designation is Not Found: " + id));

        if (designationRequestDto.getDesignationName() != null) {
            designation.setDesignationName(designationRequestDto.getDesignationName());
        }

        if (designationRequestDto.getDescription() != null) {
            designation.setDescription(designationRequestDto.getDescription());
        }

        Designation savedDesignation = designationRepository.save(designation);

        return new DesignationResponceDto(
                savedDesignation.getDesignationName(),
                savedDesignation.getDescription()
        );
    }


    // SOFT DELETE
    @Transactional
    @Override
    public void deleteDesignation(Long id) {
        Designation designation = designationRepository
                .findBydesignationIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Designation is Not Found: " + id));
        designation.setDeleted(true);
    }


    // PERMANENT DELETE
    @Override
    public void permanentlyDeleteDesignation(Long id) {
        Designation designation = designationRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Designation is Not Found: " + id));
        designationRepository.delete(designation);
    }
}