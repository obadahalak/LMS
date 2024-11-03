package com.example.lms.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryOwnerRepository extends JpaRepository<LibraryOwner,Long> {

    boolean existsByEmail(String email);

    Optional<LibraryOwner> findByEmail(String email);

}