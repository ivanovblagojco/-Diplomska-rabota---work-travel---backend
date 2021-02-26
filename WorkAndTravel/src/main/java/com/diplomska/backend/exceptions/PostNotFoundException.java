package com.diplomska.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "suppressed"})
public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(){super("Објавата не е пронајдена!");}
}
