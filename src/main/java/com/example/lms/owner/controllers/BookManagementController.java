package com.example.lms.owner.controllers;

import com.example.lms.owner.BookRequest;
import com.example.lms.owner.services.BookManagementService;
import com.example.lms.shared.OnUpdate;
import com.example.lms.shared.models.BaseResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController
@RequestMapping("/api/owner/books")
@AllArgsConstructor
@Validated
public class BookManagementController {


    private final BookManagementService bookManagementService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public BaseResponse<Object> create(@Valid @RequestBody BookRequest request) {

        bookManagementService.store(request);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .build();

    }


    @Validated(OnUpdate.class)
    @PutMapping()
    public BaseResponse<Object> update(@Valid @RequestBody BookRequest request) {

        bookManagementService.update(request);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .build();

    }


    @DeleteMapping()
    public BaseResponse<Object> delete(@RequestParam Long bookId) {

        bookManagementService.destroy(bookId);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .build();

    }
}