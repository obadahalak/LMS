package com.example.lms.patron.controllers;


import com.example.lms.patron.PatronRequest;
import com.example.lms.patron.services.PatronService;
import com.example.lms.shared.OnUpdate;
import com.example.lms.shared.models.BaseResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@AllArgsConstructor
@RequestMapping("/api/patron")
@RestController
@Validated
public class PatronController {

    private final PatronService patronService;



    @GetMapping()
    public BaseResponse<Object> index(){

        var patron = patronService.getPatron();

        return BaseResponse.builder()
                .data(patron)
                .build();
    }

    @PutMapping()
    @Validated(OnUpdate.class)
    public BaseResponse<Object> update(@Valid @RequestBody PatronRequest request){

        patronService.update(request);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .build();
    }

    @DeleteMapping()
    public BaseResponse<Object> delete(){

        patronService.destroy();

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .build();
    }
}