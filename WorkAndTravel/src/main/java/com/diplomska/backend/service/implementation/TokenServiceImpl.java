package com.diplomska.backend.service.implementation;


import com.diplomska.backend.model.Token;
import com.diplomska.backend.model.User;
import com.diplomska.backend.repository.TokenRepository;
import com.diplomska.backend.service.interfaces.TokenService;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token create(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public Token findByToken(String token) {
        return this.tokenRepository.findByToken(token);
    }

    @Override
    public void deleteByToken(String token) {
        this.tokenRepository.deleteByToken(token);
    }
}
