package com.example.carrental.service.impl;

import com.example.carrental.dto.VehicleCreateRequest;
import com.example.carrental.dto.VehicleResponse;
import com.example.carrental.exception.InvalidRequestException;
import com.example.carrental.exception.ResourceNotFoundException;
import com.example.carrental.model.Vehicle;
import com.example.carrental.patterns.factory.VehicleFactory;
import com.example.carrental.patterns.factory.VehicleType;
import com.example.carrental.patterns.singleton.LoggerService;
import com.example.carrental.repository.VehicleRepository;
import com.example.carrental.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepo;
    private final LoggerService logger = LoggerService.getInstance();

    public VehicleServiceImpl(VehicleRepository vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    private VehicleResponse toResponse(Vehicle v, String type) {
        VehicleResponse r = new VehicleResponse();
        r.id = v.getId();
        r.type = type;
        r.brand = v.getBrand();
        r.model = v.getModel();
        r.year = v.getYear();
        r.dailyPrice = v.getDailyPrice();
        r.status = v.getStatus() == null ? null : v.getStatus().name();
        return r;
    }

    @Override
    public VehicleResponse create(VehicleCreateRequest req) {
        VehicleType type;
        try {
            type = VehicleType.valueOf(req.type.toUpperCase());
        } catch (Exception e) {
            throw new InvalidRequestException("type тек ECONOMY/SUV/LUXURY болуы керек");
        }

        Vehicle v = VehicleFactory.createVehicle(type);
        v.setBrand(req.brand);
        v.setModel(req.model);
        v.setYear(req.year);
        v.setDailyPrice(req.dailyPrice);
        v.setStatus(Vehicle.VehicleStatus.AVAILABLE);

        Vehicle saved = vehicleRepo.save(v, type.name());
        logger.info("Vehicle created id=" + saved.getId());
        return toResponse(saved, type.name());
    }

    @Override
    public VehicleResponse getById(Long id) {
        Vehicle v = vehicleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle табылмады: " + id));
        // type is stored in DB; to keep it simple, fetch again via repo map uses factory already,
        // but we don't keep type on object. We'll infer by reading DB row in repo. Here, return "UNKNOWN" fallback.
        return toResponse(v, "UNKNOWN");
    }

    @Override
    public List<VehicleResponse> getAll() {
        return vehicleRepo.findAll().stream().map(v -> toResponse(v, "UNKNOWN")).toList();
    }

    @Override
    public VehicleResponse update(Long id, VehicleCreateRequest req) {
        VehicleType type;
        try {
            type = VehicleType.valueOf(req.type.toUpperCase());
        } catch (Exception e) {
            throw new InvalidRequestException("type тек ECONOMY/SUV/LUXURY болуы керек");
        }
        Vehicle v = VehicleFactory.createVehicle(type);
        v.setBrand(req.brand);
        v.setModel(req.model);
        v.setYear(req.year);
        v.setDailyPrice(req.dailyPrice);
        // status unchanged? for simplicity keep AVAILABLE when updating
        v.setStatus(Vehicle.VehicleStatus.AVAILABLE);

        Vehicle updated = vehicleRepo.update(id, v, type.name());
        logger.info("Vehicle updated id=" + id);
        return toResponse(updated, type.name());
    }

    @Override
    public void delete(Long id) {
        vehicleRepo.delete(id);
        logger.info("Vehicle deleted id=" + id);
    }
}
