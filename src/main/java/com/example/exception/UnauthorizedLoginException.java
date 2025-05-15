package com.example.exception;

public class UnauthorizedLoginException extends RuntimeException {

    public UnauthorizedLoginException(String message) {
        super(message);
    }
    
}
