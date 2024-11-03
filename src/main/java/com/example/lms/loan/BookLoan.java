package com.example.lms.loan;

import com.example.lms.book.Book;
import com.example.lms.owner.LibraryOwner;
import com.example.lms.patron.Patron;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;


@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookLoan {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnoreProperties({"libraryOwner", "bookLoans"})
    private Book book;

    @ManyToOne
    @JoinColumn(name = "library_owner_id", nullable = false)
    @JsonIgnoreProperties("books")
    private LibraryOwner libraryOwner;

    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    @JsonIgnoreProperties("bookLoans")
    private Patron patron;

    private LocalDate checkoutDate;

    private LocalDate returnDate;

    private boolean returnBack;

    private Timestamp createdAt;

    private Timestamp updatedAt;


    public void returnBack() {
        this.returnBack = true;
        this.returnDate = LocalDate.now();
    }

    @PrePersist
    public void onCreate() {
        this.returnBack = false;
        var now = new Timestamp(System.currentTimeMillis());
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}