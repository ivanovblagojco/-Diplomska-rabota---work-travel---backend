package com.diplomska.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "suppressed"})
public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(){super("Коментарот не е пронајден!");}
}
