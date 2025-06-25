package vehicles.vehicleTypes;

import vehicles.Vehicle;

public class Truck extends Vehicle {
    private static final double FUEL_CONSUMPTION_RATE = 5.0;

    public Truck(double fuelCapacity) {
        super(fuelCapacity);
    }

    @Override
    public double calculateRange() {
        return fuelCapacity / FUEL_CONSUMPTION_RATE;
    }
}
