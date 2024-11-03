package com.example.lms.patron;

import com.example.lms.shared.dto.BookDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookLoanDto {

    private LocalDate checkoutDate;

    private LocalDate returnDate;

    private boolean returnBack;

    private BookDto book;
}
