package com.example.lms.owner.services;

import com.example.lms.book.Book;
import com.example.lms.book.BookRepository;
import com.example.lms.owner.BookRequest;
import com.example.lms.owner.LibraryOwner;
import com.example.lms.owner.LibraryOwnerRepository;
import com.example.lms.shared.RequestContext;
import com.example.lms.shared.exceptions.EntityNotFoundException;
import com.example.lms.shared.exceptions.UnAuthorizeException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookManagementService {


    private final BookRepository bookRepository;

    private final LibraryOwnerRepository libraryOwnerRepository;


    public void store(BookRequest request) {

        var owner = libraryOwnerRepository.getReferenceById(RequestContext.getAuthId());

        var book = Book.builder()
                .libraryOwner(owner)
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .overview(request.getOverview())
                .pageCount(request.getPageCount())
                .title(request.getTitle())
                .publicationYear(request.getPublicationYear())
                .build();

        bookRepository.save(book);

    }

    @Transactional
    public void update(BookRequest request) {

        var owner = libraryOwnerRepository.getReferenceById(RequestContext.getAuthId());

        var book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book Not Found"));

        if(!isNotAuthorized(book,owner))
            throw new UnAuthorizeException("You don’t have access to this book");

        book.setIsbn(request.getIsbn());
        book.setTitle(request.getTitle());
        book.setOverview(request.getOverview());
        book.setPageCount(request.getPageCount());
        book.setPublicationYear(request.getPublicationYear());

        bookRepository.save(book);
    }

    @Transactional
    public void destroy(Long bookId) {

        var owner = libraryOwnerRepository.getReferenceById(RequestContext.getAuthId());

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book Not Found"));

        if(!isNotAuthorized(book,owner))
            throw new UnAuthorizeException("You don’t have access to this book");

        bookRepository.delete(book);
    }

    private Boolean isNotAuthorized(Book book, LibraryOwner owner){
        return book.getLibraryOwner().equals(owner);
    }
}