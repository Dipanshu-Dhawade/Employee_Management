    package com.management.Employee_Management.service.impl;

    import com.management.Employee_Management.dto.request.*;
    import com.management.Employee_Management.dto.responce.Employee_DocumentResponseDto;
    import com.management.Employee_Management.dto.responce.UserRegistrationResponceDto;
    import com.management.Employee_Management.dto.responce.UserResponseDto;
    import com.management.Employee_Management.model.*;
    import com.management.Employee_Management.repository.*;
    import com.management.Employee_Management.security.Config;
    import com.management.Employee_Management.security.JwtUtil;
    import com.management.Employee_Management.service.UsersService;
    import jakarta.servlet.http.HttpServletRequest;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.net.http.HttpRequest;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class UsersServiceImpl implements UsersService {

        private final UsersRepository usersRepository;
        private final EmployeeRepository employeeRepository;
        private final EmployeeDocumentRepository employee_documentRepository;
        private final EmployeeEducationRepository employeeEducationRepository;
        private final EmployeeExperienceRepository employeeExprienceRepository;
        private final DesignationRepository designationRepository;
        private final DepartmentRepository departmentRepository;
        private final JwtUtil jwtUtil;
        private final Config config;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;
        private final Employee_DocumentServiceImpl employeeDocumentService;
        private final FileStorageService fileStorageService;

        @Override
        public UserResponseDto addEmployeeUser(UserRequestDto request) {

            Users checkUser = usersRepository.findByEmail(request.getEmail());
            if (checkUser != null) {
                throw new RuntimeException("User already exists with email: " + request.getEmail());
            }

            Role role = roleRepository.findByRoleType(request.getRole().toUpperCase());
            Users user = new Users();

            user.setUsername(request.getUsername());
            user.setPassword(config.getPasswordEncoder().encode(request.getPassword()));
            user.setEmail(request.getEmail());
            user.setUpdate_at(LocalDate.now());
            user.setRole(role);
            Users saved = usersRepository.save(user);

            return mapToResponse(saved);
        }


        @Transactional(rollbackFor = Exception.class)
        @Override
        public UserRegistrationResponceDto addRegistrationForEmployee(
                UserRegistrationRequestDto request, List<MultipartFile> files, HttpServletRequest httprequest) throws IOException {

            // 1. Check username
            Users checkUser = usersRepository.findByUsername(request.getUsername());

            if (checkUser != null) {
                throw new RuntimeException("Username already exists: " + request.getUsername());
            }


            // 2. Check email
            Users checkEmail = usersRepository.findByEmail(request.getEmail());

            if (checkEmail != null) {
                throw new RuntimeException("Email already exists: " + request.getEmail());
            }


            // 3. Check phone
            Employee checkEmployee = employeeRepository.findByPhone(request.getPhone());

            if (checkEmployee != null) {
                throw new RuntimeException("Phone already exists: " + request.getPhone());
            }


            // 4. Validate designation ID
            if (request.getDesignationId() == null) {throw new RuntimeException("Designation ID is required");}

            Designation designation = designationRepository.findById(request.getDesignationId())
                            .orElseThrow(() -> new RuntimeException("Designation not found: " + request.getDesignationId()));


            // 5. Validate department ID
            if (request.getDepartmentId() == null) {
                throw new RuntimeException("Department ID is required");
            }

            Department department = departmentRepository.findById(request.getDepartmentId())
                            .orElseThrow(() -> new RuntimeException("Department not found: " + request.getDepartmentId()));


            // 6. Get USER role
            Role role = roleRepository.findByRoleType("USER");

            if (role == null) {
                throw new RuntimeException("USER role not found");
            }

            if (role.getId() == null) {
                throw new RuntimeException("USER role ID is null");
            }


            // 7. Create user
            Users user = new Users();
            user.setUsername(request.getUsername());
            // IMPORTANT: encode password
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());
            user.setUpdate_at(LocalDate.now());
            user.setRole(role);

            // 8. Create employee
            Employee employee = new Employee();
            employee.setEmployeeCode(request.getEmployee_code());
            employee.setFirstName(request.getFirst_name());
            employee.setLastName(request.getLast_name());
            employee.setDateOfBirth(request.getDate_of_birth());
            employee.setDateOfJoining(request.getDate_of_joining());
            employee.setStatus(request.getStatus());

            employee.setDesignation(designation);
            employee.setDepartment(department);
            employee.setUserId(user);


            // 9. Save user first
            Users saveUser = usersRepository.save(user);

            // 10. Save employee
            Employee saveEmployee = employeeRepository.save(employee);

            // 11. Education
            List<EmployeeEducationRequestDto> educations =
                    request.getEmployeeEducations();

            if (educations == null || educations.isEmpty()) {
                throw new RuntimeException(
                        "At least one employee education is required"
                );
            }

            for (EmployeeEducationRequestDto dto : educations) {

                EmployeeEducation education = new EmployeeEducation();

                education.setQualification(dto.getQualification());
                education.setSpecialization(dto.getSpecialization());
                education.setInstitutionName(dto.getInstitution_name());
                education.setUniversityName(dto.getUniversity_name());
                education.setPassingYear(dto.getPassing_year());
                education.setPercentage(dto.getPercentage());
                education.setGrade(dto.getGrade());

                education.setEmployee(saveEmployee);

                employeeEducationRepository.save(education);
            }



            // 13. Experience
            List<EmployeeExperienceRequestDto> experiences =
                    request.getEmployeeExperiences();

            if (experiences != null) {

                for (EmployeeExperienceRequestDto dto : experiences) {

                    EmployeeExperience experience = new EmployeeExperience();
                    experience.setCompanyName(dto.getCompany_name());
                    experience.setStartDate(dto.getStart_date());
                    experience.setEndDate(dto.getEnd_date());
                    experience.setCurrentlyWorking(dto.getCurrently_working());
                    experience.setEmployee(saveEmployee);

                    employeeExprienceRepository.save(experience);
                }
            }
            // 12. Documents
            List<Employee_DocumentRequestDto> documents =
                    request.getEmployeeDocuments();

            if (documents != null && !documents.isEmpty()) {

                if (files == null || files.size() != documents.size()) {
                    throw new RuntimeException(
                            "Number of documents and uploaded files must match"
                    );
                }

                for (int i = 0; i < documents.size(); i++) {

                    Employee_DocumentRequestDto dto = documents.get(i);
                    MultipartFile file = files.get(i);

                    if (file == null || file.isEmpty()) {
                        throw new RuntimeException(
                                "File is required for document: "
                                        + dto.getDocument_type()
                        );
                    }

                    EmployeeDocument document = new EmployeeDocument();
                    // JSON data
                    document.setDocumentType(dto.getDocument_type());
                    // Uploaded file
                    document.setFileName(file.getOriginalFilename());

                    // Your file-storage logic should return the URL
                    String fileUrl = fileStorageService.store(file);
                    document.setFile_url(fileUrl);
                    document.setUploadedAt(LocalDate.now());
                    // Automatically connect document with employee
                    document.setEmployee(saveEmployee);
                    document.setDeleted(false);

                    employee_documentRepository.save(document);
                }
            }
            // 14. Create JWT
            String jwt = jwtUtil.createJwt(saveUser);


            // 15. Response
            return new UserRegistrationResponceDto(
                    saveEmployee.getEmployeeId(),
                    saveEmployee.getFirstName(),
                    jwt
            );
        }


        @Override
        public UserResponseDto getUserById(Long id) {
            Users user = usersRepository.findById(id).
                    orElseThrow(() -> new RuntimeException("User is not found: " + id));
            if (user.is_active()) {
                throw new RuntimeException("User is not found: " + id);
            }
            return mapToResponse(user);
        }

        @Override
        public List<UserResponseDto> getAllUsers() {
            return usersRepository.findAll()
                    .stream()
                    .filter(user -> !user.is_active())
                    .map(this::mapToResponse)
                    .toList();
        }

        @Override
        public UserResponseDto updateUser(Long id, UserRequestDto request) {
            Users user = usersRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("User is not found: " + id));

            if (user.is_active()) {
                throw new RuntimeException("User is not found: " + id);
            }
            user.setUsername(request.getUsername());
            user.setPassword(config.getPasswordEncoder().encode(request.getPassword()));
            user.setEmail(request.getEmail());
            user.setUpdate_at(LocalDate.now());


            Users saved = usersRepository.save(user);
            return mapToResponse(saved);
        }


        // PATCH USER
        @Override
        public UserResponseDto patchUser(Long id, UserRequestDto request) {
            Users user = usersRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("User is not found: " + id));

            if (user.is_active()) {
                throw new RuntimeException("User is not found: " + id);
            }
            if (request.getUsername() != null) {
                user.setUsername(request.getUsername());
            }
            if (request.getPassword() != null) {
                user.setPassword(request.getPassword());
            }
            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }
            user.setUpdate_at(LocalDate.now());
            Users saved = usersRepository.save(user);
            return mapToResponse(saved);
        }


        // SOFT DELETE USER
        @Override
        public void deleteUser(Long id) {
            Users user = usersRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("User is not found: " + id));
            user.set_active(true);
            usersRepository.save(user);
        }

        @Override
        public void deleteUserPermently(Long id) {
            Users users = usersRepository.findById(id).
                    orElseThrow(() -> new RuntimeException("User is Not Found " + id));
             usersRepository.deleteById(id);
        }

        // RESTORE USER
        @Override
        public void restoreUser(Long id) {
            Users user = usersRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User is not found: " + id));
            user.set_active(false);
            usersRepository.save(user);
        }

        // ENTITY -> RESPONSE DTO
        private UserResponseDto mapToResponse(Users user) {
            return new UserResponseDto(
                    user.getUser_id(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getUpdate_at()
            );
        }
    }
