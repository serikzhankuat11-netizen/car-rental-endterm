package com.example.carrental.service;

import com.example.carrental.dto.RentalCreateRequest;
import com.example.carrental.dto.RentalResponse;

import java.util.List;

public interface RentalService {
    RentalResponse create(RentalCreateRequest req);
    RentalResponse getById(Long id);
    List<RentalResponse> getAll();
    void cancel(Long id);
}
