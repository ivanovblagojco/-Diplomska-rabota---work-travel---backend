package com.diplomska.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "suppressed"})
public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(){super("Улогата не е пронајдена!");}
}
