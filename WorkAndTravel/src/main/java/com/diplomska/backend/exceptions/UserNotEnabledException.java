package com.diplomska.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "suppressed"})
public class UserNotEnabledException extends RuntimeException{
    public UserNotEnabledException(){super("Корисникот не е одобрен!");}
}
