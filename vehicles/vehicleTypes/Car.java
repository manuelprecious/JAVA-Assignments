package vehicles.vehicleTypes;

import vehicles.Vehicle;

public class Car extends Vehicle {
    private static final double FUEL_CONSUMPTION_RATE = 10.0;

    public Car(double fuelCapacity) {
        super(fuelCapacity);
    }

    @Override
    public double calculateRange() {
        return fuelCapacity / FUEL_CONSUMPTION_RATE;
    }
}
