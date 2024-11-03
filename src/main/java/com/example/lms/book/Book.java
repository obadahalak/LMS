package com.example.lms.book;


import com.example.lms.loan.BookLoan;
import com.example.lms.owner.LibraryOwner;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.util.List;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String author;

    private Integer publicationYear;

    private String isbn;

    private Integer pageCount;

    @ManyToOne()
    @JsonIgnoreProperties("books")
    private LibraryOwner libraryOwner;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("book")
    private List<BookLoan> bookLoans;

    private Timestamp createdAt;

    private Timestamp updatedAt;


    @PrePersist
    public void onCreate() {
        var now = new Timestamp(System.currentTimeMillis());
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

}