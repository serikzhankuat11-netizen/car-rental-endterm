package com.example.carrental.service;

import com.example.carrental.dto.VehicleCreateRequest;
import com.example.carrental.dto.VehicleResponse;

import java.util.List;

public interface VehicleService {
    VehicleResponse create(VehicleCreateRequest req);
    VehicleResponse getById(Long id);
    List<VehicleResponse> getAll();
    VehicleResponse update(Long id, VehicleCreateRequest req);
    void delete(Long id);
}
