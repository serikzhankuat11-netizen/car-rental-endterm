package com.example.carrental.repository;

import com.example.carrental.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    Customer save(Customer c);
    Customer update(Long id, Customer c);
    void delete(Long id);
}
