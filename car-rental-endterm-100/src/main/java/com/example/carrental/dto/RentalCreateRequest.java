package com.example.carrental.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class RentalCreateRequest {
    @NotNull public Long customerId;
    @NotNull public Long vehicleId;
    @NotNull public LocalDate startDate;
    @NotNull public LocalDate endDate;
}
