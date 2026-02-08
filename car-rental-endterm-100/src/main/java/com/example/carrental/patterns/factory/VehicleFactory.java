package com.example.carrental.patterns.factory;

import com.example.carrental.model.*;

public class VehicleFactory {
    public static Vehicle createVehicle(VehicleType type) {
        return switch (type) {
            case ECONOMY -> new EconomyCar();
            case SUV -> new SUVCar();
            case LUXURY -> new LuxuryCar();
        };
    }
}
