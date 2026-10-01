package com.management.Employee_Management.controller;

import com.management.Employee_Management.service.EmployeeExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class XLController {

    private final EmployeeExcelService employeeExcelService;

    @GetMapping("/download-excel")
    public ResponseEntity<byte[]> downloadEmployeeExcel()
            throws IOException
    {

        byte[] excelFile =
                employeeExcelService.generateEmployeeExcel();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=employees.xlsx"
                )
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        )
                )
                .body(excelFile);
    }
}
