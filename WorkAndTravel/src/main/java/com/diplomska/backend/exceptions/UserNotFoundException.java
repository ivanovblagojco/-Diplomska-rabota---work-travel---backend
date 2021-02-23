package com.diplomska.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "suppressed"})
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){super("Корисникот не е пронајден!");}
}
