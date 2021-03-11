package com.diplomska.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "suppressed"})
public class TokenHasExpireException extends RuntimeException{
    public TokenHasExpireException(){super("Токенот е изминат. Креирајте ново барање за регистрација!");}
}
