package com.example.lms.owner;

import com.example.lms.shared.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {


    @NotNull(groups = OnUpdate.class)
    private Long bookId;

    @NotNull
    private String title;

    @NotNull
    private Integer publicationYear;

    @NotNull
    private String isbn;

    @NotNull
    private String author;

    @NotNull
    private Integer pageCount;

    @NotNull
    private String overview;
}