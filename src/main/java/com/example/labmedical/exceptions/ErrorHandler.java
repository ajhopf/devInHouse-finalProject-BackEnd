package com.example.labmedical.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<String> entityNotFound(WrongCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDto>> badRequest(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getFieldErrors();

        return ResponseEntity.badRequest().body(
                errors.stream()
                        .map(ValidationErrorDto::new)
                        .collect(Collectors.toList()));
    }
}
