package com.diplomska.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "suppressed"})
public class ApplicationNotFoundException extends RuntimeException{
    public ApplicationNotFoundException(){super("Апликацијата не е пронајдена!");}
}
