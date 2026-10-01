package com.management.Employee_Management.service;

import com.management.Employee_Management.dto.request.Employee_DocumentRequestDto;
import com.management.Employee_Management.dto.responce.Employee_DocumentResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeDocumentService {

    // CREATE
    public List<Employee_DocumentResponseDto> addDocuments(
            List<Employee_DocumentRequestDto> documentRequests,
            List<MultipartFile> files,
            HttpServletRequest request) throws IOException;

    // READ
    public Employee_DocumentResponseDto getDocumentById(Long id);

    public List<Employee_DocumentResponseDto> getAllDocuments();

    // UPDATE
    public Employee_DocumentResponseDto updateDocument(
            Long id,
            Employee_DocumentRequestDto document,
            MultipartFile file);
    // PATCH
    public Employee_DocumentResponseDto patchDocument(Long id, Employee_DocumentRequestDto document);

    // SOFT DELETE
    public void deleteDocument(Long id);

    // RESTORE
    public void PermantlyDeleteDocument(Long id);
}