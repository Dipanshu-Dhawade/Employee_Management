package com.management.Employee_Management.service.impl;

import com.management.Employee_Management.dto.request.LoginRequestDto;
import com.management.Employee_Management.dto.request.SignUpUserRequestDto;
import com.management.Employee_Management.dto.responce.LoginResponseDto;
import com.management.Employee_Management.dto.responce.SignUpUserResponceDto;
import com.management.Employee_Management.model.Role;
import com.management.Employee_Management.model.Users;
import com.management.Employee_Management.repository.RoleRepository;
import com.management.Employee_Management.repository.UsersRepository;
import com.management.Employee_Management.security.JwtUtil;
import com.management.Employee_Management.service.LoginService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public LoginResponseDto login(LoginRequestDto request) throws  AccessDeniedException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                                                                            request.getUsername(),
                                                                                            request.getPassword()));

        Users user = usersRepository.findByUsername(request.getUsername());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        String jwt = jwtUtil.createJwt(user);
        String refreshToken = jwtUtil.createRefreshToken(user);

        return new LoginResponseDto(
                user.getUser_id(),
                user.getUsername(),
                user.getRole().getRoleType(),
                jwt,
                refreshToken
        );
    }

    @Override
    @Transactional
    public SignUpUserResponceDto addRegistrationForEmployee(
            SignUpUserRequestDto requestDto){

        // 3. Check username
        Users existingUser =
                usersRepository.findByUsername(requestDto.getUsername());

        if (existingUser != null) {
            throw new RuntimeException("Username already exists");
        }

        // 4. Check email
        Users existingEmail =
                usersRepository.findByEmail(requestDto.getEmail());

        if (existingEmail != null) {
            throw new RuntimeException("Email already exists");
        }

        // 5. Get USER role
        Role role =
                roleRepository.findByRoleType("USER");

        if (role == null) {
            throw new RuntimeException("USER role not found");
        }

        // 6. Create user
        Users user = new Users();

        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        requestDto.getPassword()
                )
        );

        user.setUpdate_at(LocalDate.now());
        user.setRole(role);

        // 7. Save user
        Users savedUser = usersRepository.save(user);

        // 8. Generate JWT if you want to return it
        String jwt = jwtUtil.createJwt(savedUser);

        // 9. Response
        return new SignUpUserResponceDto(
                savedUser.getUser_id(),
                savedUser.getUsername());
    }
}