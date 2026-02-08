package com.example.carrental.service.impl;

import com.example.carrental.dto.CustomerCreateRequest;
import com.example.carrental.dto.CustomerResponse;
import com.example.carrental.exception.ResourceNotFoundException;
import com.example.carrental.model.Customer;
import com.example.carrental.patterns.singleton.LoggerService;
import com.example.carrental.repository.CustomerRepository;
import com.example.carrental.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepo;
    private final LoggerService logger = LoggerService.getInstance();

    public CustomerServiceImpl(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    private CustomerResponse toResponse(Customer c) {
        CustomerResponse r = new CustomerResponse();
        r.id = c.getId();
        r.firstName = c.getFirstName();
        r.lastName = c.getLastName();
        r.phone = c.getPhone();
        r.email = c.getEmail();
        return r;
    }

    @Override
    public CustomerResponse create(CustomerCreateRequest req) {
        Customer c = new Customer();
        c.setFirstName(req.firstName);
        c.setLastName(req.lastName);
        c.setPhone(req.phone);
        c.setEmail(req.email);

        Customer saved = customerRepo.save(c);
        logger.info("Customer created id=" + saved.getId());
        return toResponse(saved);
    }

    @Override
    public CustomerResponse getById(Long id) {
        Customer c = customerRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer табылмады: " + id));
        return toResponse(c);
    }

    @Override
    public List<CustomerResponse> getAll() {
        return customerRepo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public CustomerResponse update(Long id, CustomerCreateRequest req) {
        Customer c = new Customer();
        c.setFirstName(req.firstName);
        c.setLastName(req.lastName);
        c.setPhone(req.phone);
        c.setEmail(req.email);

        Customer updated = customerRepo.update(id, c);
        logger.info("Customer updated id=" + id);
        return toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        customerRepo.delete(id);
        logger.info("Customer deleted id=" + id);
    }
}
