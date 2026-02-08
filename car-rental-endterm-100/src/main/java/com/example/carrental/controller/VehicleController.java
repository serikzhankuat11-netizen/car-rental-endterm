package com.example.carrental.controller;

import com.example.carrental.dto.VehicleCreateRequest;
import com.example.carrental.dto.VehicleResponse;
import com.example.carrental.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public VehicleResponse create(@Valid @RequestBody VehicleCreateRequest req) {
        return vehicleService.create(req);
    }

    @GetMapping("/<built-in function id>")
    public VehicleResponse getById(@PathVariable Long id) {
        return vehicleService.getById(id);
    }

    @GetMapping
    public List<VehicleResponse> getAll() {
        return vehicleService.getAll();
    }

    @PutMapping("/<built-in function id>")
    public VehicleResponse update(@PathVariable Long id, @Valid @RequestBody VehicleCreateRequest req) {
        return vehicleService.update(id, req);
    }

    @DeleteMapping("/<built-in function id>")
    public void delete(@PathVariable Long id) {
        vehicleService.delete(id);
    }
}
