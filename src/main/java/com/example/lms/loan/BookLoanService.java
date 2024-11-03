package com.example.lms.loan;

import com.example.lms.book.BookService;
import com.example.lms.owner.services.LibraryOwnerService;
import com.example.lms.patron.services.AuthPatronService;
import com.example.lms.shared.RequestContext;
import com.example.lms.shared.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final AuthPatronService patronService;
    private final LibraryOwnerService libraryOwnerService;
    private final BookService bookService;

    @Transactional
    public void borrow(BookLoanRequest request){

        var patron = patronService.getPatron(RequestContext.getAuthId());

        var book = bookService.getBook(request.getBookId());

        var owner = libraryOwnerService.getOwner(book.getLibraryOwner().getId());

        var bookLoan = BookLoan.builder()
                  .book(book)
                  .libraryOwner(owner)
                  .checkoutDate(LocalDate.now())
                  .patron(patron)
                  .build();

        bookLoanRepository.save(bookLoan);
    }

    @Transactional
    public void returnBack(Long bookId){

        var patron = patronService.getPatron(RequestContext.getAuthId());

        var book = bookService.getBook(bookId);

        var bookLoan = bookLoanRepository.findByPatronAndBook(patron,book).orElseThrow(
                ()-> new EntityNotFoundException("Book not found")
        );

        bookLoan.returnBack();

        bookLoanRepository.save(bookLoan);
    }
}