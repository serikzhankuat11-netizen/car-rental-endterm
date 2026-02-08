package com.example.carrental.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentalResponse {
    public Long id;
    public Long customerId;
    public Long vehicleId;
    public LocalDate startDate;
    public LocalDate endDate;
    public BigDecimal totalPrice;
    public String status;
}
