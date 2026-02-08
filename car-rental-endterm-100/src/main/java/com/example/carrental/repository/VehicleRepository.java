package com.example.carrental.repository;

import com.example.carrental.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {
    Optional<Vehicle> findById(Long id);
    List<Vehicle> findAll();
    Vehicle save(Vehicle v, String type);
    Vehicle update(Long id, Vehicle v, String type);
    void delete(Long id);
    void updateStatus(Long id, Vehicle.VehicleStatus status);
}
