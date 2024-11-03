package com.example.lms.token;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;


    public void store(String token) {
        var userToken = Token.builder()
                .accessToken(token)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(userToken);

    }

    public boolean isValidToken(String token) {
        return tokenRepository.findByAccessToken(token).map(t ->
                !t.isExpired() && !t.isRevoked()
        ).orElse(false);
    }

}