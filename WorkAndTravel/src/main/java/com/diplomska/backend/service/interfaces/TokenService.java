package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.model.Token;
import com.diplomska.backend.model.User;

import java.util.List;

public interface TokenService {
    Token create (Token token);
    Token findByToken (String token);
    void deleteByToken(String token);
}
