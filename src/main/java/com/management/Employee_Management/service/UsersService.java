package com.management.Employee_Management.service;

import com.management.Employee_Management.dto.request.UserRegistrationRequestDto;
import com.management.Employee_Management.dto.request.UserRequestDto;
import com.management.Employee_Management.dto.responce.UserRegistrationResponceDto;
import com.management.Employee_Management.dto.responce.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UsersService {

    // CREATE
    UserResponseDto addEmployeeUser(UserRequestDto request);

    public UserRegistrationResponceDto addRegistrationForEmployee(UserRegistrationRequestDto request, List<MultipartFile> files , HttpServletRequest httprequest) throws IOException;
        // READ
    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    // UPDATE
    UserResponseDto updateUser(
            Long id,
            UserRequestDto request);

    // PATCH
    UserResponseDto patchUser(
            Long id,
            UserRequestDto request);

    // SOFT DELETE
    void deleteUser(Long id);
    void deleteUserPermently(Long id);
    // RESTORE
    void restoreUser(Long id);
}