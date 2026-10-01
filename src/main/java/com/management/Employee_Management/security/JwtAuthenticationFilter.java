package com.management.Employee_Management.security;

import com.management.Employee_Management.model.Users;
import com.management.Employee_Management.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil authUti;
    private final UsersRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        // 1. No JWT
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {

            // 2. Extract JWT claims
            Claims claims = authUti.extractClaims(token);

            String username = claims.getSubject();
            Users user = usersRepository.findByUsername(username);

            String role = claims.get("role", String.class);
            List<String> permissions = claims.get("permissions", List.class);
            // 3. Authorities
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (role != null) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            if (permissions != null) {
                permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
            }
            // 4. Authentication
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            authorities
                    );
            // 5. Set SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Debug
            System.out.println("USER = " + username);
            System.out.println("ROLE = " + role);
            System.out.println("AUTHORITIES = " + authorities);

        } catch (Exception e) {

            System.out.println("Invalid JWT: " + e.getMessage());
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        // 6. Continue
        filterChain.doFilter(request, response);
    }
}