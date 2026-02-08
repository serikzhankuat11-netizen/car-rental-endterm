package com.example.carrental.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class VehicleCreateRequest {
    @NotBlank
    public String type; // ECONOMY/SUV/LUXURY

    @NotBlank
    public String brand;

    @NotBlank
    public String model;

    @Min(1980) @Max(2100)
    public int year;

    @NotNull @DecimalMin(value = "0.0", inclusive = true)
    public BigDecimal dailyPrice;
}
