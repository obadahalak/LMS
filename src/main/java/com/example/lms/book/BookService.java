package com.example.lms.book;

import com.example.lms.shared.dto.BookDto;
import com.example.lms.shared.exceptions.EntityNotFoundException;
import com.example.lms.shared.utils.GenericMap;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {


    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public Page<BookDto> getBooks(int pageNumber) {

        var books = bookRepository.findAll(
                PageRequest.of(pageNumber, 10)
        );

        return GenericMap.mapPage(books, BookDto.class);
    }

    public Book getBook(Long bookId) {

      return bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Book Not Found")
      );

    }
}