package com.diplomska.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "suppressed"})
public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException(){super("Фајлот не е пронајден!");}
}
