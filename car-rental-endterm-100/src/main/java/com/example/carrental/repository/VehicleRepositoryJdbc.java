package com.example.carrental.repository;

import com.example.carrental.exception.ResourceNotFoundException;
import com.example.carrental.model.Vehicle;
import com.example.carrental.patterns.factory.VehicleFactory;
import com.example.carrental.patterns.factory.VehicleType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class VehicleRepositoryJdbc implements VehicleRepository {

    private final JdbcTemplate jdbc;

    public VehicleRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private Vehicle mapVehicle(java.util.Map<String, Object> row) {
        String type = (String) row.get("type");
        Vehicle v = VehicleFactory.createVehicle(VehicleType.valueOf(type));
        v.setId(((Number) row.get("vehicle_id")).longValue());
        v.setBrand((String) row.get("brand"));
        v.setModel((String) row.get("model"));
        v.setYear(((Number) row.get("year")).intValue());
        v.setDailyPrice((java.math.BigDecimal) row.get("daily_price"));
        v.setStatus(Vehicle.VehicleStatus.valueOf((String) row.get("status")));
        return v;
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        List<java.util.Map<String, Object>> rows = jdbc.queryForList(
                "SELECT vehicle_id, type, brand, model, year, daily_price, status FROM car_rental.vehicles WHERE vehicle_id=?",
                id
        );
        if (rows.isEmpty()) return Optional.empty();
        return Optional.of(mapVehicle(rows.get(0)));
    }

    @Override
    public List<Vehicle> findAll() {
        List<java.util.Map<String, Object>> rows = jdbc.queryForList(
                "SELECT vehicle_id, type, brand, model, year, daily_price, status FROM car_rental.vehicles ORDER BY vehicle_id"
        );
        return rows.stream().map(this::mapVehicle).toList();
    }

    @Override
    public Vehicle save(Vehicle v, String type) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO car_rental.vehicles(type, brand, model, year, daily_price, status) VALUES (?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, type);
            ps.setString(2, v.getBrand());
            ps.setString(3, v.getModel());
            ps.setInt(4, v.getYear());
            ps.setBigDecimal(5, v.getDailyPrice());
            ps.setString(6, v.getStatus() == null ? "AVAILABLE" : v.getStatus().name());
            return ps;
        }, kh);

        Number key = kh.getKey();
        if (key != null) v.setId(key.longValue());
        if (v.getStatus() == null) v.setStatus(Vehicle.VehicleStatus.AVAILABLE);
        return v;
    }

    @Override
    public Vehicle update(Long id, Vehicle v, String type) {
        int updated = jdbc.update(
            "UPDATE car_rental.vehicles SET type=?, brand=?, model=?, year=?, daily_price=?, status=? WHERE vehicle_id=?",
            type, v.getBrand(), v.getModel(), v.getYear(), v.getDailyPrice(),
            (v.getStatus() == null ? "AVAILABLE" : v.getStatus().name()),
            id
        );
        if (updated == 0) throw new ResourceNotFoundException("Vehicle табылмады: " + id);
        v.setId(id);
        return v;
    }

    @Override
    public void delete(Long id) {
        jdbc.update("DELETE FROM car_rental.vehicles WHERE vehicle_id=?", id);
    }

    @Override
    public void updateStatus(Long id, Vehicle.VehicleStatus status) {
        jdbc.update("UPDATE car_rental.vehicles SET status=? WHERE vehicle_id=?", status.name(), id);
    }
}
