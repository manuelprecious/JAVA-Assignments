package vehicles.vehicleTypes;

import vehicles.Vehicle;

public class Motorcycle extends Vehicle {
    private static final double FUEL_CONSUMPTION_RATE = 15.0;

    public Motorcycle(double fuelCapacity) {
        super(fuelCapacity);
    }

    public double calculateRange() {
        return fuelCapacity / FUEL_CONSUMPTION_RATE;
    }
}
