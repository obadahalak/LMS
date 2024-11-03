package com.example.lms.shared.exceptions;

public class UnAuthorizeException extends RuntimeException {
    public UnAuthorizeException(String msg) {
        super(msg);
    }
}