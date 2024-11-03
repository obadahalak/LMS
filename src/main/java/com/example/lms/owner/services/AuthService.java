package com.example.lms.owner.services;

import com.example.lms.owner.LibraryOwner;
import com.example.lms.owner.LibraryOwnerRepository;
import com.example.lms.owner.LibraryOwnerRequest;
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
public class AuthService {

    private final LibraryOwnerRepository libraryOwnerRepository;

    private final TokenService tokenService;

    private final JwtUtil jwtUtil;


    public void register(LibraryOwnerRequest request) {

        var owner = LibraryOwner.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .build();

        libraryOwnerRepository.save(owner);

    }

    public LoginResponse login(LibraryOwnerRequest request) {


        var owner = libraryOwnerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("email or password is not correct"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(request.getPassword(), owner.getPassword()))
            throw new BadCredentialsException("email or password is not correct");


        var accessToken = jwtUtil.generateToken(
                jwtPayload(owner),
                owner
        );

        tokenService.store(accessToken);

        return LoginResponse.builder()
                .token(accessToken)
                .userEmail(owner.getEmail())
                .build();

    }

    private Map<String, Object> jwtPayload(LibraryOwner libraryOwner) {

        Map<String, Object> payload = new HashMap<>();

        payload.put("role", Role.OWNER);
        payload.put("authId", libraryOwner.getId());
        payload.put("authEmail", libraryOwner.getEmail());

        return payload;

    }
}