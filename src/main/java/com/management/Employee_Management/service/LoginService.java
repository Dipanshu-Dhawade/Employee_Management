package com.management.Employee_Management.service;

import com.management.Employee_Management.dto.request.LoginRequestDto;
import com.management.Employee_Management.dto.request.SignUpUserRequestDto;
import com.management.Employee_Management.dto.responce.LoginResponseDto;
import com.management.Employee_Management.dto.responce.SignUpUserResponceDto;
import org.springframework.security.core.Authentication;

import java.nio.file.AccessDeniedException;

public interface LoginService {

    LoginResponseDto login(LoginRequestDto request)throws AccessDeniedException;
    public SignUpUserResponceDto addRegistrationForEmployee(SignUpUserRequestDto requestDto);
}