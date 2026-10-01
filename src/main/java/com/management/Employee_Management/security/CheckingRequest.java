package com.management.Employee_Management.security;

import com.management.Employee_Management.model.*;
import com.management.Employee_Management.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckingRequest {

     private  final EmployeeDocumentRepository employeeDocumentRepository;
     private  final EmployeeEducationRepository employeeEducationRepository;
     private final EmployeeExperienceRepository employeeExperienceRepository;
    private final DepartmentRepository departmentRepository;
    private final  EmployeeRepository employeeRepository;
    private final  CustomerRepository customerRepository;

     //admin can access anything
     //Check jwt id and  userid  is match -> can accesss
     //              otherwise
     //                   NO
    public void check(Authentication authentication, Long userId) {
        if(authentication==null )throw  new RuntimeException("Authication is null");
        Users user = (Users) authentication.getPrincipal();
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth ->
                        auth.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return;
        }
        if (user == null) {throw new RuntimeException("Patient profile not found");}
        if (!user.getUser_id().equals(userId)) {throw new RuntimeException("You can update only your own patient profile");}
    }


    public  boolean  RoleISAdmin(Authentication authentication ){
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth ->
                        auth.getAuthority().equals("ROLE_ADMIN"));

        return isAdmin;
    }

    //for Admin  return  directly
    // if it is  User it have permission
    public void checkAuthority(Authentication authentication, String permission) {

        if (authentication == null) {
            throw new RuntimeException("Authentication is null");
        }

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(auth ->
                        auth.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return;
        }

        boolean isUser = authentication.getAuthorities()
                .stream()
                .anyMatch(auth ->
                        auth.getAuthority().equals("ROLE_USER"));

        boolean hasPermission = authentication.getAuthorities()
                .stream()
                .anyMatch(auth ->
                        auth.getAuthority().equals(permission));

        if (!isUser) {
            throw new RuntimeException("You must have USER role");
        }

        if (!hasPermission) {
            throw new RuntimeException(
                    "You do not have " + permission + " permission");
        }
    }


    public Long getUserIdFormDocId(Long DocId) {
        EmployeeDocument employeeDocument = employeeDocumentRepository.findById(DocId).orElseThrow(() -> new RuntimeException("Employee Doc is not creater"));
        Employee employee = employeeDocument.getEmployee();
        return employee.getEmployeeId();

    }

    public Long getUserIdFormEduId(Long Eduid) {
        EmployeeEducation employeeEducation = employeeEducationRepository.findById(Eduid)
                .orElseThrow(() -> new RuntimeException("Employee Doc is not creater"));
        Employee employee = employeeEducation.getEmployee();
        return employee.getEmployeeId();
    }

    public Long getUserIdFormExpId(Long Expid) {
        EmployeeExperience employeeExperience = employeeExperienceRepository.findById(Expid)
                .orElseThrow(() -> new RuntimeException("Employee Doc is not creater"));
        Employee employee = employeeExperience.getEmployee();
        return employee.getEmployeeId();
    }

    public Long getUserIdfromEmployeeId(Long empId){
        Employee employee = employeeRepository.findById(empId).
                orElseThrow(() -> new RuntimeException("Employee id Is not Found " + empId));
        return  employee.getUserId().getUser_id();
    }

    public Long getUserIdFormDeptId(Long Deptid) {
        Employee employee = employeeRepository.findByDepartment_departmentId(Deptid);
        return employee.getUserId().getUser_id();
    }

    public Long getUserIdFormDesignationId(Long designationid) {
        Employee employee = employeeRepository.findByDesignation_DesignationId(designationid);
        return employee.getEmployeeId();
    }

    public Long getUserIdFormCustomerId(Long customerid) {
        Customer customer = customerRepository.findById(customerid).
                orElseThrow(()->new RuntimeException("Customer is Not Found "+customerid));
        return customer.getUsersId().getUser_id();
    }

    public boolean RoleisHr(Authentication authentication) {
        boolean roleHr = authentication.getAuthorities()
                .stream().
                anyMatch(s -> s.getAuthority().equals("ROLE_HR"));
        return roleHr;
    }


}