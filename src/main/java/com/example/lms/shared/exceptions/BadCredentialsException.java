package com.example.lms.shared.exceptions;

public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException(String msg) {
        super(msg);

    }
}