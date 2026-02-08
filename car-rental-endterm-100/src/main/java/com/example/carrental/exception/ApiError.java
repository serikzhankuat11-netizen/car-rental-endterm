package com.example.carrental.exception;

import java.time.LocalDateTime;

public class ApiError {
    public LocalDateTime timestamp = LocalDateTime.now();
    public int status;
    public String error;
    public String message;
    public String path;

    public ApiError(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
