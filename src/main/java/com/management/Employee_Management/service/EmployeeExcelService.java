package com.management.Employee_Management.service;

import com.management.Employee_Management.model.Employee;
import com.management.Employee_Management.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeExcelService {

    private final EmployeeRepository employeeRepository;

    public byte[] generateEmployeeExcel() throws IOException {

        List<Employee> employees = employeeRepository.findAll();

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Employees");

        // Header
        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("First Name");
        header.createCell(2).setCellValue("Last Name");
        header.createCell(3).setCellValue("Email");
        header.createCell(4).setCellValue("Phone");

        // Data
        int rowNum = 1;

        for (Employee employee : employees) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(employee.getEmployeeId());
            row.createCell(1).setCellValue(employee.getFirstName());
            row.createCell(2).setCellValue(employee.getLastName());
            row.createCell(3).setCellValue(employee.getUserId().getEmail());
            row.createCell(4).setCellValue(employee.getPhone());
        }

        // Auto-size columns
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}