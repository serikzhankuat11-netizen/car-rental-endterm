package com.example.carrental.repository;

import com.example.carrental.exception.ResourceNotFoundException;
import com.example.carrental.model.Rental;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class RentalRepositoryJdbc implements RentalRepository {

    private final JdbcTemplate jdbc;

    public RentalRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private Rental map(java.util.Map<String, Object> row) {
        Rental r = new Rental();
        r.setId(((Number) row.get("rental_id")).longValue());
        r.setCustomerId(((Number) row.get("customer_id")).longValue());
        r.setVehicleId(((Number) row.get("vehicle_id")).longValue());
        r.setStartDate(((java.sql.Date) row.get("start_date")).toLocalDate());
        r.setEndDate(((java.sql.Date) row.get("end_date")).toLocalDate());
        r.setTotalPrice((java.math.BigDecimal) row.get("total_price"));
        r.setStatus(Rental.RentalStatus.valueOf((String) row.get("status")));
        return r;
    }

    @Override
    public Optional<Rental> findById(Long id) {
        List<java.util.Map<String, Object>> rows = jdbc.queryForList(
                "SELECT rental_id, customer_id, vehicle_id, start_date, end_date, total_price, status FROM car_rental.rentals WHERE rental_id=?",
                id
        );
        if (rows.isEmpty()) return Optional.empty();
        return Optional.of(map(rows.get(0)));
    }

    @Override
    public List<Rental> findAll() {
        List<java.util.Map<String, Object>> rows = jdbc.queryForList(
                "SELECT rental_id, customer_id, vehicle_id, start_date, end_date, total_price, status FROM car_rental.rentals ORDER BY rental_id"
        );
        return rows.stream().map(this::map).toList();
    }

    @Override
    public Rental save(Rental r) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO car_rental.rentals(customer_id, vehicle_id, start_date, end_date, total_price, status) VALUES (?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setLong(1, r.getCustomerId());
            ps.setLong(2, r.getVehicleId());
            ps.setDate(3, java.sql.Date.valueOf(r.getStartDate()));
            ps.setDate(4, java.sql.Date.valueOf(r.getEndDate()));
            ps.setBigDecimal(5, r.getTotalPrice());
            ps.setString(6, r.getStatus() == null ? "CREATED" : r.getStatus().name());
            return ps;
        }, kh);

        Number key = kh.getKey();
        if (key != null) r.setId(key.longValue());
        if (r.getStatus() == null) r.setStatus(Rental.RentalStatus.CREATED);
        return r;
    }

    @Override
    public void updateStatus(Long id, Rental.RentalStatus status) {
        int updated = jdbc.update("UPDATE car_rental.rentals SET status=? WHERE rental_id=?", status.name(), id);
        if (updated == 0) throw new ResourceNotFoundException("Rental табылмады: " + id);
    }

    @Override
    public void delete(Long id) {
        jdbc.update("DELETE FROM car_rental.rentals WHERE rental_id=?", id);
    }
}
