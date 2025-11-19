package com.devsu.bankingcustomer.infrastructure.web;

import com.devsu.bankingcustomer.application.dto.ErrorDetail;
import com.devsu.bankingcustomer.application.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", Instant.now().toString(),
                        "message", ex.getMessage()
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
