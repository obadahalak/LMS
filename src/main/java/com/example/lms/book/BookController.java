package com.example.lms.book;

import com.example.lms.shared.models.BaseResponse;
import com.example.lms.shared.models.PaginateResponse;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
@Validated
public class BookController {

    private final BookService bookService;


    @GetMapping()
    public PaginateResponse<Object> all(@RequestParam Integer page) {

        var books = bookService.getBooks(page);

        return PaginateResponse.builder()
                .data(books.getContent())
                .last(books.isLast())
                .totalPages(books.getTotalPages())
                .build();

    }

    @GetMapping("/book")
    public BaseResponse<Object> show(@RequestParam Long bookId) {

        var book = bookService.getBook(bookId);

        return BaseResponse.builder()
                .data(book)
                .build();

    }

}