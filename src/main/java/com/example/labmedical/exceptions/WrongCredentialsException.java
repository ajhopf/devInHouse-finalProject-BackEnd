package com.example.labmedical.exceptions;

public class WrongCredentialsException extends GeneralException {
    public WrongCredentialsException() {
        super("Email ou senha informados não conferem.");
    }
}
