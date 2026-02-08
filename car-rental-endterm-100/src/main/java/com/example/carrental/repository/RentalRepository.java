package com.example.carrental.repository;

import com.example.carrental.model.Rental;

import java.util.List;
import java.util.Optional;

public interface RentalRepository {
    Optional<Rental> findById(Long id);
    List<Rental> findAll();
    Rental save(Rental r);
    void updateStatus(Long id, Rental.RentalStatus status);
    void delete(Long id);
}
