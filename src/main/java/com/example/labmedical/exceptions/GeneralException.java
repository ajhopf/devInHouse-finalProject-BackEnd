package com.example.labmedical.exceptions;

public class GeneralException extends RuntimeException {
    public GeneralException() {}
    public GeneralException(String message) {
        super(message);
    }
}

