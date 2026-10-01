package com.management.Employee_Management.service.impl;

import com.management.Employee_Management.dto.request.EmployeeRequestDto;
import com.management.Employee_Management.dto.responce.EmployeeResponseDto;
import com.management.Employee_Management.model.Employee;
import com.management.Employee_Management.model.Users;
import com.management.Employee_Management.repository.DepartmentRepository;
import com.management.Employee_Management.repository.DesignationRepository;
import com.management.Employee_Management.repository.EmployeeRepository;
import com.management.Employee_Management.repository.UsersRepository;
import com.management.Employee_Management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UsersRepository usersRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;

    @Override
    public EmployeeResponseDto addEmployee(EmployeeRequestDto request) {

        Employee employee = new Employee();

        employee.setEmployeeCode(request.getEmployee_code());
        employee.setFirstName(request.getFirst_name());
        employee.setLastName(request.getLast_name());
        employee.setPhone(request.getPhone());
        employee.setDateOfBirth(request.getDate_of_birth());
        employee.setDateOfJoining(request.getDate_of_joining());
        employee.setStatus(request.getStatus());

       Users user = usersRepository.findById(request.getUser_id()).
                orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUser_id()));

        if (request.getUser_id() != null) {
            employee.setUserId(user);
        }
        if (request.getDepartment_id() != null) {
            employee.setDepartment(departmentRepository.findById(request.getDepartment_id())
                            .orElseThrow(() -> new RuntimeException("Department not found with id: " + request.getDepartment_id()))
            );
        }
        if (request.getDesignation_id() != null) {
            employee.setDesignation(designationRepository.findById(request.getDesignation_id())
                            .orElseThrow(() -> new RuntimeException("Designation not found with id: " + request.getDesignation_id()))
            );
        }
        Employee saved = employeeRepository.save(employee);

        return mapToResponse(saved);
    }


    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        if (employee.isDeleted()) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        return mapToResponse(employee);
    }


    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .filter(employee -> !employee.isDeleted())
                .map(this::mapToResponse)
                .toList();
    }


    @Override
    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto request) {
        Employee employee = employeeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        if (employee.isDeleted()) {
            throw new RuntimeException("Employee not found with id: " + id);
        }

        employee.setEmployeeCode(request.getEmployee_code());
        employee.setFirstName(request.getFirst_name());
        employee.setLastName(request.getLast_name());
        employee.setPhone(request.getPhone());
        employee.setDateOfBirth(request.getDate_of_birth());
        employee.setDateOfJoining(request.getDate_of_joining());
        employee.setStatus(request.getStatus());

        if (request.getUser_id() != null) {
            employee.setUserId(usersRepository.findById(request.getUser_id())
                            .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUser_id())));
        }

        if (request.getDepartment_id() != null) {
            employee.setDepartment(
                    departmentRepository.findById(request.getDepartment_id())
                            .orElseThrow(() -> new RuntimeException("Department not found with id: " + request.getDepartment_id())));
        }
        if (request.getDesignation_id() != null) {
            employee.setDesignation(designationRepository.findById(request.getDesignation_id())
                            .orElseThrow(() -> new RuntimeException("Designation not found with id: " + request.getDesignation_id())));
        }
        Employee saved = employeeRepository.save(employee);

        return mapToResponse(saved);
    }


    @Override
    public EmployeeResponseDto patchEmployee(
            Long id,
            EmployeeRequestDto request) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        if (employee.isDeleted()) {
            throw new RuntimeException("Employee not found with id: " + id);
        }

        if (request.getEmployee_code() != null) {
            employee.setEmployeeCode(request.getEmployee_code());
        }

        if (request.getFirst_name() != null) {
            employee.setFirstName(request.getFirst_name());
        }

        if (request.getLast_name() != null) {
            employee.setLastName(request.getLast_name());
        }

        if (request.getPhone() != null) {
            employee.setPhone(request.getPhone());
        }

        if (request.getDate_of_birth() != null) {
            employee.setDateOfBirth(request.getDate_of_birth());
        }

        if (request.getDate_of_joining() != null) {
            employee.setDateOfJoining(request.getDate_of_joining());
        }

        if (request.getStatus() != null) {
            employee.setStatus(request.getStatus());
        }

        if (request.getUser_id() != null) {
            employee.setUserId(usersRepository.findById(request.getUser_id())
                            .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUser_id()))
            );
        }

        if (request.getDepartment_id() != null) {
            employee.setDepartment(departmentRepository.findById(request.getDepartment_id()).
                    orElseThrow(() -> new RuntimeException("Department not found with id: " + request.getDepartment_id())));
        }

        if (request.getDesignation_id() != null) {
            employee.setDesignation(designationRepository.findById(request.getDesignation_id()).
                            orElseThrow(() -> new RuntimeException("Designation not found with id: " + request.getDesignation_id())));
        }
        Employee saved = employeeRepository.save(employee);
        return mapToResponse(saved);
    }


    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employee.setDeleted(true);
        employeeRepository.save(employee);
    }


    @Override
    public void restoreEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).
                        orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employee.setDeleted(false);
        employeeRepository.save(employee);
    }

    public void PermetelyDelete(Long id){
        Employee employee = employeeRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
       employeeRepository.deleteById(id);
    }


    private EmployeeResponseDto mapToResponse(Employee employee) {

        return new EmployeeResponseDto(
                employee.getEmployeeId(),
                employee.getEmployeeCode(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPhone(),
                employee.getDateOfBirth(),
                employee.getDateOfJoining(),
                employee.getStatus(),

                employee.getUserId() != null
                        ? employee.getUserId().getUser_id()
                        : null,

                employee.getDepartment() != null
                        ? employee.getDepartment().getDepartmentId()
                        : null,

                employee.getDesignation() != null
                        ? employee.getDesignation().getDesignationId()
                        : null
        );
    }
}