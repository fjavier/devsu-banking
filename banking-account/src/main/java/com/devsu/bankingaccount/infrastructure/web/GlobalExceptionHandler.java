package com.devsu.bankingaccount.infrastructure.web;

import com.devsu.bankingaccount.application.dto.ErrorDetail;
import com.devsu.bankingaccount.application.dto.ErrorMessage;
import com.devsu.bankingaccount.domain.exception.SaldoNoDisponibleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
}
