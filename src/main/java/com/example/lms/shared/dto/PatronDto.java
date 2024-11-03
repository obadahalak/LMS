package com.example.lms.shared.dto;

import com.example.lms.patron.BookLoanDto;
import lombok.Data;
import java.util.List;

@Data
public class PatronDto {

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private List<BookLoanDto> bookLoans;

}