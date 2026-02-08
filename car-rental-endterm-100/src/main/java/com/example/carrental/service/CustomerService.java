package com.example.carrental.service;

import com.example.carrental.dto.CustomerCreateRequest;
import com.example.carrental.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerCreateRequest req);
    CustomerResponse getById(Long id);
    List<CustomerResponse> getAll();
    CustomerResponse update(Long id, CustomerCreateRequest req);
    void delete(Long id);
}
