package com.example.lms.shared.dto;

import lombok.Data;

@Data
public class BookDto {

    private Long id;

    private String title;

    private String author;

    private LibraryOwnerDto libraryOwner;

    private Integer publicationYear;

    private String isbn;

    private Integer pageCount;
}