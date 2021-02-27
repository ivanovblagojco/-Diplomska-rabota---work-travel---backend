package com.diplomska.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public UserNotFoundException handleUserNotFoundException (UserNotFoundException unf) {
        return unf;
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public PostNotFoundException handlePostNotFoundException (PostNotFoundException pnf) {
        return pnf;
    }

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RoleNotFoundException handleRoleNotFoundException (RoleNotFoundException rnf) {
        return rnf;
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public FileNotFoundException handleFileNotFoundException (FileNotFoundException fnf) {
        return fnf;
    }

}