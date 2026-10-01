package com.management.Employee_Management.controller;

import com.management.Employee_Management.dto.request.UserRegistrationRequestDto;
import com.management.Employee_Management.dto.request.UserRequestDto;
import com.management.Employee_Management.dto.responce.UserRegistrationResponceDto;
import com.management.Employee_Management.dto.responce.UserResponseDto;
import com.management.Employee_Management.model.Users;
import com.management.Employee_Management.security.CheckingRequest;
import com.management.Employee_Management.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final CheckingRequest checkingRequest;
    private final ObjectMapper objectMapper;

    // ADD USER
    @PostMapping
    //@PreAuthorize("hasAuthority('CREATE_USER')")
    public ResponseEntity<UserResponseDto> addEmployeeUser(@RequestBody UserRequestDto request) {
        UserResponseDto response = usersService.addEmployeeUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // COMPLETE EMPLOYEE REGISTRATION
    @PostMapping(value = "/employee-registration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserRegistrationResponceDto> register(
                                                                @RequestPart("data")
                                                                String data,
                                                                @RequestPart(value = "files", required = false)
                                                                List<MultipartFile> files,
                                                                HttpServletRequest httpServletRequest
    ) throws IOException {
        UserRegistrationRequestDto request = objectMapper.readValue(data, UserRegistrationRequestDto.class);
        UserRegistrationResponceDto response = usersService.addRegistrationForEmployee(request, files, httpServletRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id,
                                                       Authentication authentication) {
        checkingRequest.checkAuthority(authentication, "READ_User");
        checkingRequest.check(authentication, id);

        UserResponseDto response = usersService.getUserById(id);
        return ResponseEntity.ok(response);
    }
    // GET ALL USERS
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> response = usersService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                      @RequestBody UserRequestDto request,
                                                      Authentication authentication) {
        checkingRequest.checkAuthority(authentication, "UPDATE_USER");
        checkingRequest.check(authentication, id);

        UserResponseDto response = usersService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }

    // PATCH USER
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> patchUser(@PathVariable Long id,
                                                     @RequestBody UserRequestDto request,
                                                     Authentication authentication) {
        checkingRequest.checkAuthority(authentication, "UPDATE_USER");
        checkingRequest.check(authentication, id);

        UserResponseDto response = usersService.patchUser(id, request);
        return ResponseEntity.ok(response);
    }

    // SOFT DELETE USER
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id,
                                             Authentication authentication) {

        usersService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @DeleteMapping("/{id}/permanent")
   // @PreAuthorize("hasAuthority('DELETE_USER')")
    public ResponseEntity<String> deleteUserPermently(@PathVariable Long id) {
        usersService.deleteUserPermently(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    // RESTORE USER
    @PatchMapping("/{id}/restore")
   // @PreAuthorize("hasAuthority('DELETE_USER')")
    public ResponseEntity<String> restoreUser(@PathVariable Long id) {
        usersService.restoreUser(id);
        return ResponseEntity.ok("User restored successfully");
    }
}