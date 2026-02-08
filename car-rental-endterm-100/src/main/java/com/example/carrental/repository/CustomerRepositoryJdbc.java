package com.example.carrental.repository;

import com.example.carrental.exception.ResourceNotFoundException;
import com.example.carrental.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepositoryJdbc implements CustomerRepository {

    private final JdbcTemplate jdbc;

    public CustomerRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private Customer map(java.util.Map<String, Object> row) {
        Customer c = new Customer();
        c.setId(((Number) row.get("customer_id")).longValue());
        c.setFirstName((String) row.get("first_name"));
        c.setLastName((String) row.get("last_name"));
        c.setPhone((String) row.get("phone"));
        c.setEmail((String) row.get("email"));
        return c;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        List<java.util.Map<String, Object>> rows = jdbc.queryForList(
                "SELECT customer_id, first_name, last_name, phone, email FROM car_rental.customers WHERE customer_id=?",
                id
        );
        if (rows.isEmpty()) return Optional.empty();
        return Optional.of(map(rows.get(0)));
    }

    @Override
    public List<Customer> findAll() {
        List<java.util.Map<String, Object>> rows = jdbc.queryForList(
                "SELECT customer_id, first_name, last_name, phone, email FROM car_rental.customers ORDER BY customer_id"
        );
        return rows.stream().map(this::map).toList();
    }

    @Override
    public Customer save(Customer c) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO car_rental.customers(first_name, last_name, phone, email) VALUES (?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getEmail());
            return ps;
        }, kh);

        Number key = kh.getKey();
        if (key != null) c.setId(key.longValue());
        return c;
    }

    @Override
    public Customer update(Long id, Customer c) {
        int updated = jdbc.update(
            "UPDATE car_rental.customers SET first_name=?, last_name=?, phone=?, email=? WHERE customer_id=?",
            c.getFirstName(), c.getLastName(), c.getPhone(), c.getEmail(), id
        );
        if (updated == 0) throw new ResourceNotFoundException("Customer табылмады: " + id);
        c.setId(id);
        return c;
    }

    @Override
    public void delete(Long id) {
        jdbc.update("DELETE FROM car_rental.customers WHERE customer_id=?", id);
    }
}
