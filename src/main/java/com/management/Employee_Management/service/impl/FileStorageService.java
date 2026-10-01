package com.management.Employee_Management.service.impl;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

@Service
public class FileStorageService {

    private final Path uploadDir = Paths.get("uploads");

    public FileStorageService() {
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    public String store(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        try {
            String fileName = System.currentTimeMillis()
                    + "_" + file.getOriginalFilename();

            Path targetPath = uploadDir.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    targetPath
            );

            return targetPath.toString();

        } catch (IOException e) {
            throw new RuntimeException("Could not store file", e);
        }
    }
}