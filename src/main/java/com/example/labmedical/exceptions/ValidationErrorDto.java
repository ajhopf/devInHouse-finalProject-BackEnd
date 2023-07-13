package com.example.labmedical.exceptions;

import org.springframework.validation.FieldError;

public class ValidationErrorDto {
    private final String field;
    private final String message;

    public ValidationErrorDto(FieldError erro) {
        this.field = erro.getField();
        this.message = erro.getDefaultMessage();
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
