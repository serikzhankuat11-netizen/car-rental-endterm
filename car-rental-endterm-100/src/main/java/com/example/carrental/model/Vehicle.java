package com.example.carrental.model;

import java.math.BigDecimal;

public abstract class Vehicle {
    private Long id;
    private String brand;
    private String model;
    private int year;
    private BigDecimal dailyPrice;
    private VehicleStatus status;

    public enum VehicleStatus { AVAILABLE, RENTED, MAINTENANCE }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public BigDecimal getDailyPrice() { return dailyPrice; }
    public void setDailyPrice(BigDecimal dailyPrice) { this.dailyPrice = dailyPrice; }

    public VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleStatus status) { this.status = status; }
}
