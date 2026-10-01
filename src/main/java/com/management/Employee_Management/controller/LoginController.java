package com.management.Employee_Management.controller;
import com.management.Employee_Management.dto.request.SignUpUserRequestDto;
import com.management.Employee_Management.dto.responce.SignUpUserResponceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import com.management.Employee_Management.dto.request.LoginRequestDto;
import com.management.Employee_Management.dto.responce.LoginResponseDto;
import com.management.Employee_Management.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    @PostMapping("/signup")
    public ResponseEntity<SignUpUserResponceDto> signup(@RequestBody SignUpUserRequestDto request) {
        SignUpUserResponceDto response = loginService.addRegistrationForEmployee(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) throws AccessDeniedException {
        LoginResponseDto response = loginService.login(request );
        return ResponseEntity.ok(response);
    }
}