package com.example.carrental.patterns.builder;

import com.example.carrental.model.Rental;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentalBuilder {
    private final Rental rental = new Rental();

    public RentalBuilder customerId(Long v) { rental.setCustomerId(v); return this; }
    public RentalBuilder vehicleId(Long v) { rental.setVehicleId(v); return this; }
    public RentalBuilder startDate(LocalDate v) { rental.setStartDate(v); return this; }
    public RentalBuilder endDate(LocalDate v) { rental.setEndDate(v); return this; }
    public RentalBuilder totalPrice(BigDecimal v) { rental.setTotalPrice(v); return this; }
    public RentalBuilder status(Rental.RentalStatus v) { rental.setStatus(v); return this; }

    public Rental build() {
        if (rental.getCustomerId() == null || rental.getVehicleId() == null) {
            throw new IllegalArgumentException("customerId және vehicleId міндетті");
        }
        if (rental.getStartDate() == null || rental.getEndDate() == null) {
            throw new IllegalArgumentException("startDate және endDate міндетті");
        }
        return rental;
    }
}
