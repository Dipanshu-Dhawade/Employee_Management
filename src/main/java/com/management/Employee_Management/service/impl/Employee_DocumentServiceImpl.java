package com.management.Employee_Management.service.impl;

import com.management.Employee_Management.dto.request.Employee_DocumentRequestDto;
import com.management.Employee_Management.dto.responce.Employee_DocumentResponseDto;
import com.management.Employee_Management.model.Employee;
import com.management.Employee_Management.model.EmployeeDocument;
import com.management.Employee_Management.repository.EmployeeDocumentRepository;
import com.management.Employee_Management.repository.EmployeeRepository;
import com.management.Employee_Management.service.EmployeeDocumentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Employee_DocumentServiceImpl implements EmployeeDocumentService {

    private final EmployeeDocumentRepository empdocumentRepository;
    private final EmployeeRepository employeeRepository;
    private final  FileStorageService fileStorageService;

    @Override
    public List<Employee_DocumentResponseDto> addDocuments(
            List<Employee_DocumentRequestDto> documentRequests,
            List<MultipartFile> files,
            HttpServletRequest request) throws IOException {

        if (files == null || files.isEmpty()) {
            throw new RuntimeException("Documents are empty");
        }

        if (documentRequests == null || documentRequests.isEmpty()) {
            throw new RuntimeException("Document details are empty");
        }

        if (documentRequests.size() != files.size()) {
            throw new RuntimeException(
                    "Number of documents and files must be the same"
            );
        }

        List<Employee_DocumentResponseDto> responses =
                new ArrayList<>();

        String realPath =
                request.getServletContext().getRealPath("");

        Path uploadPath =
                Paths.get(realPath, "uploads");

        Files.createDirectories(uploadPath);

        for (int i = 0; i < files.size(); i++) {

            MultipartFile file = files.get(i);

            Employee_DocumentRequestDto documentRequest =
                    documentRequests.get(i);

            // Get employee ID from DTO
            Long employeeId = documentRequest.getEmployeeId();

            if (employeeId == null) {
                throw new RuntimeException(
                        "Employee ID is required at index: " + i
                );
            }

            Employee employee =
                    employeeRepository.findById(employeeId)
                            .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

            if (file == null || file.isEmpty()) {
                throw new RuntimeException(
                        "File is empty at index: " + i
                );
            }

            String originalFilename =
                    file.getOriginalFilename();

            if (originalFilename == null ||
                    originalFilename.isBlank()) {

                throw new RuntimeException(
                        "File name is empty at index: " + i
                );
            }

            EmployeeDocument existingDocument =
                    empdocumentRepository.findByFileName(
                            originalFilename
                    );

            if (existingDocument != null) {
                throw new RuntimeException(
                        "File already exists: "
                                + originalFilename
                );
            }

            Path filePath =
                    uploadPath.resolve(originalFilename);

            Files.write(
                    filePath,
                    file.getBytes()
            );

            EmployeeDocument document =
                    new EmployeeDocument();

            document.setDocumentType(
                    documentRequest.getDocument_type()
            );

            document.setFileName(
                    originalFilename
            );

            document.setFile_url(
                    filePath.toString()
            );

            document.setUploadedAt(
                    LocalDate.now()
            );

            document.setEmployee(employee);

            EmployeeDocument savedDocument =
                    empdocumentRepository.save(document);

            Employee_DocumentResponseDto response =
                    new Employee_DocumentResponseDto(
                            savedDocument.getDocumentType(),
                            savedDocument.getFileName(),
                            savedDocument.getFile_url(),
                            savedDocument.getUploadedAt()
                    );

            responses.add(response);
        }

        return responses;
    }
    @Override
    public Employee_DocumentResponseDto getDocumentById(Long id) {
        EmployeeDocument employeeDocument = empdocumentRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Your Document is Not Found" + id));

        return new Employee_DocumentResponseDto(employeeDocument.getDocumentType(),
                employeeDocument.getFileName(),
                employeeDocument.getFile_url(),
                employeeDocument.getUploadedAt());
    }

    @Override
    public List<Employee_DocumentResponseDto> getAllDocuments() {
        List<EmployeeDocument> allDocument = empdocumentRepository.findAll();
        List<Employee_DocumentResponseDto> responseList = new ArrayList<>();
        for (EmployeeDocument document : allDocument) {
            if (!document.isDeleted()) {

                Employee_DocumentResponseDto response = new Employee_DocumentResponseDto();
                response.setFile_name(document.getFileName());
                response.setDocument_type(document.getDocumentType());
                response.setFile_url(document.getFile_url());

                responseList.add(response);
            }
        }
        return responseList;
    }

    @Override
    public Employee_DocumentResponseDto updateDocument(
            Long id,
            Employee_DocumentRequestDto document,
            MultipartFile file) {

        EmployeeDocument employeeDocument =
                empdocumentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Document is Not Found " + id));

        if (employeeDocument.isDeleted()) {
            throw new RuntimeException(
                    "Document is already deleted " + id);
        }

        employeeDocument.setFileName(document.getFile_name());
        employeeDocument.setDocumentType(document.getDocument_type());

        // If a new file is uploaded
        if (file != null && !file.isEmpty()) {

            // save file and get URL
            String fileUrl = fileStorageService.store(file);

            employeeDocument.setFile_url(fileUrl);
            employeeDocument.setFileName(file.getOriginalFilename());
        }

        EmployeeDocument updated = empdocumentRepository.save(employeeDocument);
        return new Employee_DocumentResponseDto(
                updated.getDocumentType(),
                updated.getFileName(),
                updated.getFile_url(),
                updated.getUploadedAt()
        );
    }

    @Override
    public Employee_DocumentResponseDto patchDocument(
            Long id,
            Employee_DocumentRequestDto document) {

        EmployeeDocument employeeDocument =
                empdocumentRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Document is Not Found " + id));

        if (employeeDocument.isDeleted()) {
            throw new RuntimeException("Document is already deleted " + id);
        }

        if (document.getFile_name() != null) {
            employeeDocument.setFileName(document.getFile_name());
        }

        if (document.getDocument_type() != null) {
            employeeDocument.setDocumentType(document.getDocument_type());
        }

        if (document.getFile_url() != null) {
            employeeDocument.setFile_url(document.getFile_url());
        }

        EmployeeDocument updated =
                empdocumentRepository.save(employeeDocument);

        return new Employee_DocumentResponseDto(
                updated.getDocumentType(),
                updated.getFileName(),
                updated.getFile_url(),
                updated.getUploadedAt()
        );
    }

    @Override
    public void deleteDocument(Long id) {
        EmployeeDocument employeeDocument = empdocumentRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Document is Not Found" + id));
        if (employeeDocument.isDeleted()) throw new RuntimeException("It is already deleted " + id);

        employeeRepository.deleteById(id);
    }

    @Override
    public void PermantlyDeleteDocument(Long id) {
        EmployeeDocument employeeDocument = empdocumentRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Document is Not avalable " + id));
        empdocumentRepository.deleteById(id);
    }
}
