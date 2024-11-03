package com.example.lms.owner.controllers;


import com.example.lms.owner.services.AuthService;
import com.example.lms.owner.LibraryOwnerRequest;
import com.example.lms.shared.OnCreate;
import com.example.lms.shared.models.BaseResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@AllArgsConstructor
@RequestMapping("/api/owner/auth")
@Validated
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public BaseResponse<Object> login(@Valid @RequestBody LibraryOwnerRequest request){

        var userToken = authService.login(request);

        return BaseResponse.builder()
                .data(userToken)
                .build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    @PostMapping("/register")
    public BaseResponse<Object> register(@Valid() @RequestBody LibraryOwnerRequest request){

       authService.register(request);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .build();
    }

}