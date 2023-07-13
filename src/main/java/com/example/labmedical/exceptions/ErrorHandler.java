package com.example.labmedical.exceptions;

<<<<<<< HEAD
=======
import com.example.labmedical.service.LogService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> origin/dev
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

<<<<<<< HEAD
import java.util.List;
=======
import java.util.HashMap;
import java.util.List;
import java.util.Map;
>>>>>>> origin/dev
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {
<<<<<<< HEAD
    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<String> entityNotFound(WrongCredentialsException e) {
=======
    @Autowired
    private LogService logger;
    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<String> entityNotFound(WrongCredentialsException e) {
        logger.error("Entidade não encontrada " + e.getMessage());
>>>>>>> origin/dev
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
<<<<<<< HEAD
    public ResponseEntity<List<ValidationErrorDto>> badRequest(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getFieldErrors();

        return ResponseEntity.badRequest().body(
                errors.stream()
                        .map(ValidationErrorDto::new)
                        .collect(Collectors.toList()));
    }
=======
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
>>>>>>> origin/dev
}
