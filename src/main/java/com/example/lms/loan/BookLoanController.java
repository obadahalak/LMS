package com.example.lms.loan;


import com.example.lms.shared.models.BaseResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/loan")
@AllArgsConstructor
@Validated
public class BookLoanController {

    private final BookLoanService bookLoanService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public BaseResponse<Object> store(@Valid @RequestBody BookLoanRequest request){

        bookLoanService.borrow(request);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .build();

    }

    @PutMapping("/return")
    public BaseResponse<Object> returnBack(@RequestParam Long bookId){

        bookLoanService.returnBack(bookId);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .build();

    }
}