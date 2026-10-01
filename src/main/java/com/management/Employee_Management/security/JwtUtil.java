package com.management.Employee_Management.security;

import com.management.Employee_Management.model.Permission;
import com.management.Employee_Management.model.RefreshToken;
import com.management.Employee_Management.model.Users;
import com.management.Employee_Management.repository.RefreshTokenRepository;
import com.management.Employee_Management.repository.Role_PermissionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final Role_PermissionRepository rolePermissionRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${screateKey}")
    private String secreateKey;

    public SecretKey getSecreteKey() {
        return Keys.hmacShaKeyFor(secreateKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createJwt(Users saveUser) {
        List<Permission> allPermissions = rolePermissionRepository.findAllPermissionByRoleId(saveUser.getRole().getId());
        List<String> permissionNames = allPermissions.stream()
                .map(Permission::getPermission)
                .toList();

        return Jwts.builder()
                // Use username because your filter uses findByUsername()
                .subject(saveUser.getUsername())
                .claim("userId", saveUser.getUser_id())
                .claim("username", saveUser.getUsername())
                // Store only the role name
                .claim("role", saveUser.getRole().getRoleType())
                .claim("permissions", permissionNames)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSecreteKey())
                .compact();
    }

    public String extractUsername(String token) {

        return Jwts.parser()
                .verifyWith(getSecreteKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }

    public String createRefreshToken(Users user) {

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedAt(LocalDateTime.now());
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(30));
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    public Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSecreteKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}