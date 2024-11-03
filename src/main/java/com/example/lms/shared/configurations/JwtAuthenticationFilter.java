package com.example.lms.shared.configurations;

import com.example.lms.shared.utils.JwtUtil;
import com.example.lms.shared.utils.Role;
import com.example.lms.token.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final ObjectMapper objectMapper;

    private final JwtUtil jwtUtil;

    private final TokenService tokenService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) {

        try {


            if (    request.getServletPath().contains("api/owner/auth") ||
                    request.getServletPath().contains("api/patron/auth") ||
                    request.getServletPath().contains("api/books")
            ) {

                filterChain.doFilter(request, response);
                return;
            }

            if (request.getServletPath().contains("/api/owner")) {


                if (isAuthenticated(request, Role.OWNER))
                    filterChain.doFilter(request, response);
                else
                    unauthorized(response);

            }

            if (request.getServletPath().contains("/api/patron")) {


                if (isAuthenticated(request, Role.PATRON))
                    filterChain.doFilter(request, response);
                else
                    unauthorized(response);

            }

            if (request.getServletPath().contains("/api/loan")) {

                if (isAuthenticated(request, Role.PATRON))
                    filterChain.doFilter(request, response);
                else
                    unauthorized(response);

            }


        } catch (Exception ex) {
            unauthorized(response);
        }

    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer "))
            return null;

        return authHeader.substring(7);
    }

    private void unauthorized(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("error", "UNAUTHORIZED"));
    }

    private boolean isAuthenticated(HttpServletRequest request, Role role) {
        String token = extractToken(request);

        return (jwtUtil.isNotTokenExpiredAndHasAuthority(token, role) && tokenService.isValidToken(token));
    }

}