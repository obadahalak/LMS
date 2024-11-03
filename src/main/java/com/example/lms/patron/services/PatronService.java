package com.example.lms.patron.services;

import com.example.lms.shared.dto.PatronDto;
import com.example.lms.patron.PatronRepository;
import com.example.lms.patron.PatronRequest;
import com.example.lms.shared.RequestContext;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatronService {

    private final PatronRepository patronRepository;
    private final ModelMapper modelMapper;


    public PatronDto getPatron() {

        var patron = patronRepository.findById(RequestContext.getAuthId()).orElseThrow();

        return modelMapper.map(patron, PatronDto.class);
    }

    public void update(PatronRequest request) {
        var patron = patronRepository.findById(RequestContext.getAuthId()).orElseThrow();

        if (!patron.getEmail().equals(request.getEmail()))
            patron.setEmail(request.getEmail());

        patron.setAddress(request.getAddress());
        patron.setName(request.getName());
        patron.setPhoneNumber(request.getPhoneNumber());

        patronRepository.save(patron);

    }

    public void destroy() {
        var patron = patronRepository.findById(RequestContext.getAuthId()).orElseThrow();
        patronRepository.delete(patron);
    }
}