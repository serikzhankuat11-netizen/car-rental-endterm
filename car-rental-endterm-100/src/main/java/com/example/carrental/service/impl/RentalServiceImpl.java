package com.example.carrental.service.impl;

import com.example.carrental.dto.RentalCreateRequest;
import com.example.carrental.dto.RentalResponse;
import com.example.carrental.exception.InvalidRequestException;
import com.example.carrental.exception.ResourceNotFoundException;
import com.example.carrental.exception.VehicleNotAvailableException;
import com.example.carrental.model.Rental;
import com.example.carrental.model.Vehicle;
import com.example.carrental.patterns.builder.RentalBuilder;
import com.example.carrental.patterns.singleton.LoggerService;
import com.example.carrental.repository.RentalRepository;
import com.example.carrental.repository.VehicleRepository;
import com.example.carrental.service.RentalService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepo;
    private final VehicleRepository vehicleRepo;
    private final LoggerService logger = LoggerService.getInstance();

    public RentalServiceImpl(RentalRepository rentalRepo, VehicleRepository vehicleRepo) {
        this.rentalRepo = rentalRepo;
        this.vehicleRepo = vehicleRepo;
    }

    private RentalResponse toResponse(Rental r) {
        RentalResponse resp = new RentalResponse();
        resp.id = r.getId();
        resp.customerId = r.getCustomerId();
        resp.vehicleId = r.getVehicleId();
        resp.startDate = r.getStartDate();
        resp.endDate = r.getEndDate();
        resp.totalPrice = r.getTotalPrice();
        resp.status = r.getStatus() == null ? null : r.getStatus().name();
        return resp;
    }

    @Override
    public RentalResponse create(RentalCreateRequest req) {
        if (req.endDate.isBefore(req.startDate)) {
            throw new InvalidRequestException("endDate startDate-тен бұрын болмауы керек");
        }

        Vehicle v = vehicleRepo.findById(req.vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle табылмады: " + req.vehicleId));

        if (v.getStatus() != Vehicle.VehicleStatus.AVAILABLE) {
            throw new VehicleNotAvailableException("Vehicle бос емес: " + req.vehicleId);
        }

        long days = ChronoUnit.DAYS.between(req.startDate, req.endDate) + 1;
        BigDecimal total = v.getDailyPrice().multiply(BigDecimal.valueOf(days));

        Rental rental = new RentalBuilder()
                .customerId(req.customerId)
                .vehicleId(req.vehicleId)
                .startDate(req.startDate)
                .endDate(req.endDate)
                .totalPrice(total)
                .status(Rental.RentalStatus.CREATED)
                .build();

        Rental saved = rentalRepo.save(rental);
        vehicleRepo.updateStatus(req.vehicleId, Vehicle.VehicleStatus.RENTED);

        logger.info("Rental created id=" + saved.getId());
        return toResponse(saved);
    }

    @Override
    public RentalResponse getById(Long id) {
        Rental r = rentalRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rental табылмады: " + id));
        return toResponse(r);
    }

    @Override
    public List<RentalResponse> getAll() {
        return rentalRepo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public void cancel(Long id) {
        Rental r = rentalRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rental табылмады: " + id));
        rentalRepo.updateStatus(id, Rental.RentalStatus.CANCELLED);
        vehicleRepo.updateStatus(r.getVehicleId(), Vehicle.VehicleStatus.AVAILABLE);
        logger.info("Rental cancelled id=" + id);
    }
}
