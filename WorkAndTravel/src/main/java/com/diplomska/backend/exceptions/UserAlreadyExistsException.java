package com.diplomska.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "suppressed"})
public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(){super("Корисникот веќе е потврден!");}
}
