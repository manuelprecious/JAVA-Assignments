package vehicles;

public abstract class Vehicle {
    protected double fuelCapacity;

    public Vehicle(double fuelCapacity) {
        if (fuelCapacity < 0) {
            throw new IllegalArgumentException("Fuel Capacity cannot be negative");
        }

        this.fuelCapacity = fuelCapacity;
    }

    public abstract double calculateRange();

    public double getFuelCapacity() {
        return fuelCapacity;
    }
}
