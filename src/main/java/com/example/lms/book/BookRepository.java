package com.example.lms.book;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {


    @EntityGraph(attributePaths = {"libraryOwner","bookLoans"})
    Page<Book> findAll(Pageable pageable);
}