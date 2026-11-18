package com.tap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRestaurantNotFound(
            RestaurantNotFoundException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                404,
                ex.getMessage()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex) {

        StringBuilder errors = new StringBuilder();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.append(error.getField())
                  .append(": ")
                  .append(error.getDefaultMessage())
                  .append("; ");
        });

        ErrorResponse errorResponse = new ErrorResponse(
                400,
                errors.toString()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST
        );
    }  // ← THIS closes handleValidationException()

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(
            ResponseStatusException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getStatusCode().value(),
                ex.getReason()
        );

        return new ResponseEntity<>(
                errorResponse,
                ex.getStatusCode()
        );
    }
}