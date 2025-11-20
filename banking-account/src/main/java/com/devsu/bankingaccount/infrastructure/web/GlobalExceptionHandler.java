package com.devsu.bankingaccount.infrastructure.web;

import com.devsu.bankingaccount.application.dto.ErrorDetail;
import com.devsu.bankingaccount.application.dto.ErrorMessage;
import com.devsu.bankingaccount.domain.exception.SaldoNoDisponibleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SaldoNoDisponibleException.class)
    public ResponseEntity<?> handleSaldoNoDisponible(SaldoNoDisponibleException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", Instant.now().toString(),
                        "mensaje", ex.getMessage()
                ));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", Instant.now().toString(),
                        "mensaje", ex.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setDate(new Date());
        List<ErrorDetail> errors = new ArrayList<>();


        ex.getBindingResult().getFieldErrors().forEach(err ->{
                    ErrorDetail errorDetail = new ErrorDetail();
                    errorDetail.setMessage(err.getDefaultMessage());
                    errors.add(errorDetail);
                }

        );
        errorMessage.setErrorDetail(errors);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleMissingParams(MissingServletRequestParameterException ex) {
        Map<String, Object> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now());
        error.put("mensaje", "El parámetro requerido '" + ex.getParameterName() + "' es obligatorio");
        error.put("tipo", ex.getParameterType());

        return error;
    }
}
