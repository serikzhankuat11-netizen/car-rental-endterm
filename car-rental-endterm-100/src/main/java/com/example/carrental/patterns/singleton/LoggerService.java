package com.example.carrental.patterns.singleton;

import java.time.LocalDateTime;

public class LoggerService {
    private static final LoggerService INSTANCE = new LoggerService();
    private LoggerService() {}

    public static LoggerService getInstance() { return INSTANCE; }

    public void info(String msg) {
        System.out.println(LocalDateTime.now() + " [INFO] " + msg);
    }
}
