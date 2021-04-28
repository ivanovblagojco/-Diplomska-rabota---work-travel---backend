package com.diplomska.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "suppressed"})
public class LocationNotFoundException extends RuntimeException{
    public LocationNotFoundException(){super("Локацијата не е пронајдена!");}
}
