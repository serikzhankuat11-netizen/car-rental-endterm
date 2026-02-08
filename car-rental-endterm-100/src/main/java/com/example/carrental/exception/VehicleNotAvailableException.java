package com.example.carrental.exception;

public class VehicleNotAvailableException extends RuntimeException {
    public VehicleNotAvailableException(String msg) { super(msg); }
}
