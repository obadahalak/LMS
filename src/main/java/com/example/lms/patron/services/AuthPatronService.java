package com.example.lms.patron.services;

import com.example.lms.patron.Patron;
import com.example.lms.patron.PatronRepository;
import com.example.lms.patron.PatronRequest;
import com.example.lms.shared.exceptions.BadCredentialsException;
import com.example.lms.shared.models.LoginResponse;
import com.example.lms.shared.utils.JwtUtil;
import com.example.lms.shared.utils.Role;
import com.example.lms.token.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthPatronService {

    private final PatronRepository patronRepository;

    private final TokenService tokenService;

    private final JwtUtil jwtUtil;

    public void register(PatronRequest request){

       var patron =  Patron.builder()
                .address(request.getAddress())
                .email(request.getEmail())
                .gender(request.getGender())
                .name(request.getName())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .build();

       patronRepository.save(patron);

    }

    public LoginResponse authenticate(PatronRequest request){

        var patron = patronRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("email or password is not correct"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(request.getPassword(), patron.getPassword()))
            throw new BadCredentialsException("email or password is not correct");


        var accessToken = jwtUtil.generateToken(
                jwtPayload(patron),
                patron
        );

        tokenService.store(accessToken);

        return LoginResponse.builder()
                .token(accessToken)
                .userEmail(patron.getEmail())
                .build();

    }

    public Patron getPatron(Long id){
       return patronRepository.getReferenceById(id);
    }

    private Map<String, Object> jwtPayload(Patron patron) {

        Map<String, Object> payload = new HashMap<>();

        payload.put("role", Role.PATRON);
        payload.put("authId", patron.getId());
        payload.put("authEmail", patron.getEmail());

        return payload;

    }
}