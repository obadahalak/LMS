package com.example.lms.loan;

import com.example.lms.book.Book;
import com.example.lms.patron.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookLoanRepository extends JpaRepository<BookLoan,Long> {

    Optional<BookLoan> findByPatronAndBook(Patron patron, Book book);
}