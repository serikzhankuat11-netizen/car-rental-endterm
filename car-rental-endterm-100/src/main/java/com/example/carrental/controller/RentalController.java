package com.example.carrental.controller;

import com.example.carrental.dto.RentalCreateRequest;
import com.example.carrental.dto.RentalResponse;
import com.example.carrental.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public RentalResponse create(@Valid @RequestBody RentalCreateRequest req) {
        return rentalService.create(req);
    }

    @GetMapping("/<built-in function id>")
    public RentalResponse getById(@PathVariable Long id) {
        return rentalService.getById(id);
    }

    @GetMapping
    public List<RentalResponse> getAll() {
        return rentalService.getAll();
    }

    @DeleteMapping("/<built-in function id>")
    public void cancel(@PathVariable Long id) {
        rentalService.cancel(id);
    }
}
