package com.example.carrental.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handle404(ResourceNotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(404).body(new ApiError(404, "NOT_FOUND", ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler({InvalidRequestException.class, VehicleNotAvailableException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiError> handle400(RuntimeException ex, HttpServletRequest req) {
        return ResponseEntity.status(400).body(new ApiError(400, "BAD_REQUEST", ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handle500(Exception ex, HttpServletRequest req) {
        return ResponseEntity.status(500).body(new ApiError(500, "INTERNAL_ERROR", ex.getMessage(), req.getRequestURI()));
    }
}
