package com.example.lms.shared;


import com.example.lms.shared.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    public final JwtUtil jwtUtil;

    public TokenInterceptor(RequestContext requestContext, JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String headerToken = request.getHeader("Authorization");

        if (headerToken != null && headerToken.startsWith("Bearer ")) {

            String token = headerToken.substring(7);

            var authId = jwtUtil.extractAllClaims(token).get("authId");

            var authEmail = jwtUtil.extractAllClaims(token).get("authEmail");

            if (Objects.nonNull(authId) && Objects.nonNull(authEmail)) {

                RequestContext.setAuthId(Long.parseLong(authId.toString()));
                RequestContext.setAuthEmail(authEmail.toString());
            }
            return true;
        }

        return true;
    }

}