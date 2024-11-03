package com.example.lms.patron.controllers;

import com.example.lms.patron.services.AuthPatronService;
import com.example.lms.patron.PatronRequest;
import com.example.lms.shared.OnCreate;
import com.example.lms.shared.models.BaseResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@AllArgsConstructor
@RequestMapping("/api/patron/auth")
@RestController
@Validated
public class AuthPatronController {

    private final AuthPatronService authPatronService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public BaseResponse<Object> register(@Valid @RequestBody PatronRequest request) {

        authPatronService.register(request);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .build();
    }

    @PostMapping("/login")
    public BaseResponse<Object> login(@Valid @RequestBody PatronRequest request) {

        var patronToken = authPatronService.authenticate(request);

        return BaseResponse.builder()
                .data(patronToken)
                .build();
    }
}