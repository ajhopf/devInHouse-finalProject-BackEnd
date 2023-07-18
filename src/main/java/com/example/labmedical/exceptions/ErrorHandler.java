package com.example.labmedical.exceptions;

import com.example.labmedical.service.LogService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {
    @Autowired
    private LogService logger;
    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<String> wrongCredentials(WrongCredentialsException e) {
        logger.error("Credenciais inválidas: " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFound(EntityNotFoundException e) {
        logger.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDto>> badRequest(MethodArgumentNotValidException ex) {
        List<FieldError> erros = ex.getFieldErrors();

        return ResponseEntity.badRequest().body(
                erros.stream()
                        .map(ValidationErrorDto::new)
                        .collect(Collectors.toList()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> trataErro400(ConstraintViolationException e) {
        var retorno = new HashMap<String, Object>();
        for(var validator : e.getConstraintViolations()) {
            var key = "";
            var value = validator.getMessage();
            for(var node : validator.getPropertyPath()) {
                key = node.getName();
            }
            retorno.put(key, value);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(retorno);
    }

    @ExceptionHandler(RegisterAlreadyExistExcepetion.class)
    public ResponseEntity<String> userExist(RegisterAlreadyExistExcepetion e){
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(EmptyUserListException.class)
    public ResponseEntity<String> emptyUserList(EmptyUserListException e){
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
