package com.example.labmedical.exceptions;

import com.example.labmedical.service.LogService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {
    @Autowired
    private LogService logger;
    @ExceptionHandler(PacientAlreadyRegisteredException.class)
    public ResponseEntity<String> pacientAlreadyRegistered(PacientAlreadyRegisteredException e) {
        logger.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> httpMessageNotReadable(HttpMessageNotReadableException e) {
        logger.error("Erro de requisição.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<String> wrongCredentials(WrongCredentialsException e) {
//        logger.error("Credenciais inválidas: " + e.getMessage());
        logger.error(e.getMessage());
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

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> emptyUserList(UserException e){
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> methodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        logger.error("Tentiva de requisição com parametros inválidos.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(PatientNotFountException.class)
    public ResponseEntity<Map<String, String>> patientNotFound(PatientNotFountException e) {
        logger.error("Tentiva de requisição com parametros inválidos.");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Erro", e.getMessage()));
    }

    @ExceptionHandler(PatientWithRecordsException.class)
    public ResponseEntity<Map<String, String>> patientWithRecords() {
        logger.error("Tentativa de deleção de paciente com registros.");

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("Erro", "Não é possível deletar o paciente pois este possui registros vinculados."));
    }
}
