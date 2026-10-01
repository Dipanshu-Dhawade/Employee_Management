package com.management.Employee_Management.controller;


import com.management.Employee_Management.model.Users;
import com.management.Employee_Management.security.CheckingRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import com.management.Employee_Management.dto.request.Employee_DocumentRequestDto;
import com.management.Employee_Management.dto.responce.Employee_DocumentResponseDto;
import com.management.Employee_Management.service.EmployeeDocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employee-documents")
@RequiredArgsConstructor
public class EmployeeDocumentController {

    private final EmployeeDocumentService employeeDocumentService;
    private final CheckingRequest checkingRequest;
    private final ObjectMapper objectMapper;


    // ADD DOCUMENT
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('CREATE_EMPLOYEE_DOCUMENT')")
    public ResponseEntity<List<Employee_DocumentResponseDto>> addDocument(

            @RequestPart("data")
            String data,

            @RequestPart("files")
            List<MultipartFile> files,

            HttpServletRequest request) throws IOException {

        List<Employee_DocumentRequestDto> documents =
                objectMapper.readValue(
                        data,
                        new TypeReference<List<Employee_DocumentRequestDto>>() {}
                );

        List<Employee_DocumentResponseDto> response =
                employeeDocumentService.addDocuments(
                        documents,
                        files,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET DOCUMENT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee_DocumentResponseDto> getDocumentById(
            @PathVariable Long id, Authentication authentication) {
        checkingRequest.checkAuthority(authentication, "READ_EMPLOYEE_DOCUMENT");
        Long userId = checkingRequest.getUserIdFormDocId(id);
        checkingRequest.check(authentication, userId);

        Employee_DocumentResponseDto response = employeeDocumentService.getDocumentById(id);
        return ResponseEntity.ok(response);
    }


    // GET ALL DOCUMENTS
    @GetMapping()
    public ResponseEntity<List<Employee_DocumentResponseDto>> getAllDocuments() {

        List<Employee_DocumentResponseDto> response = employeeDocumentService.getAllDocuments();
        return ResponseEntity.ok(response);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Employee_DocumentResponseDto> updateDocument(
            @PathVariable Long id,

            @RequestPart("data")
            String data,

            @RequestPart(value = "files", required = false)
            MultipartFile file,

            Authentication authentication) throws IOException {

        checkingRequest.checkAuthority(
                authentication,
                "UPDATE_EMPLOYEE_DOCUMENT"
        );

        Long userId =
                checkingRequest.getUserIdFormDocId(id);

        checkingRequest.check(authentication, userId);

        Employee_DocumentRequestDto document =
                objectMapper.readValue(
                        data,
                        Employee_DocumentRequestDto.class
                );

        Employee_DocumentResponseDto response =
                employeeDocumentService.updateDocument(
                        id,
                        document,
                        file
                );

        return ResponseEntity.ok(response);
    }

    // PATCH DOCUMENT
    @PatchMapping("/{id}")
    public ResponseEntity<Employee_DocumentResponseDto> patchDocument(@PathVariable Long id, @RequestBody Employee_DocumentRequestDto request,
                                                                      Authentication authentication) {
        checkingRequest.checkAuthority(authentication, "UPDATE_EMPLOYEE_DOCUMENT");
        Long userId = checkingRequest.getUserIdFormDocId(id);
        checkingRequest.check(authentication, userId);

        Employee_DocumentResponseDto response = employeeDocumentService.patchDocument(id, request);
        return ResponseEntity.ok(response);
    }


    // SOFT DELETE DOCUMENT
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id,
                                                 Authentication authentication) {
        checkingRequest.checkAuthority(authentication, "DELETE_EMPLOYEE_DOCUMENT");
        Long userId = checkingRequest.getUserIdFormDocId(id);
        checkingRequest.check(authentication, userId);

        employeeDocumentService.deleteDocument(id);
        return ResponseEntity.ok("Document deleted successfully");
    }



    // PERMANENT DELETE DOCUMENT
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<String> permanentlyDeleteDocument(@PathVariable Long id,
                                                            Authentication authentication) {
        checkingRequest.checkAuthority(authentication, "DELETE_EMPLOYEE_DOCUMENT");
        Long userId = checkingRequest.getUserIdFormDocId(id);
        checkingRequest.check(authentication, userId);

        employeeDocumentService.PermantlyDeleteDocument(id);

        return ResponseEntity.ok(
                "Document permanently deleted successfully"
        );
    }
}
