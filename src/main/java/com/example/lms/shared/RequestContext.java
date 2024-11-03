package com.example.lms.shared;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@AllArgsConstructor
public class RequestContext {

    public static Long authId;

    public static String authEmail;

    public static Long getAuthId() {
        return authId;
    }

    public static void setAuthId(Long auth_id) {
        authId = (auth_id);
    }

    public static String getAuthEmail() {
        return authEmail;
    }

    public static void setAuthEmail(String email) {
        authEmail = email;
    }
}