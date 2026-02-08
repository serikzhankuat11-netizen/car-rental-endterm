package com.example.carrental.controller;

import com.example.carrental.dto.CustomerCreateRequest;
import com.example.carrental.dto.CustomerResponse;
import com.example.carrental.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerResponse create(@Valid @RequestBody CustomerCreateRequest req) {
        return customerService.create(req);
    }

    @GetMapping("/<built-in function id>")
    public CustomerResponse getById(@PathVariable Long id) {
        return customerService.getById(id);
    }

    @GetMapping
    public List<CustomerResponse> getAll() {
        return customerService.getAll();
    }

    @PutMapping("/<built-in function id>")
    public CustomerResponse update(@PathVariable Long id, @Valid @RequestBody CustomerCreateRequest req) {
        return customerService.update(id, req);
    }

    @DeleteMapping("/<built-in function id>")
    public void delete(@PathVariable Long id) {
        customerService.delete(id);
    }
}
