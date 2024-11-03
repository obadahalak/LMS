package com.example.lms.owner.services;

import com.example.lms.owner.LibraryOwner;
import com.example.lms.owner.LibraryOwnerRepository;
import com.example.lms.shared.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LibraryOwnerService {

    private final LibraryOwnerRepository libraryOwnerRepository;


    public LibraryOwner getOwner(Long id){
       return libraryOwnerRepository.getReferenceById(id);
    }
}