package vehicles;

import java.util.ArrayList;
import java.util.List;
import vehicles.vehicleTypes.Car;
import vehicles.vehicleTypes.Truck;
import vehicles.vehicleTypes.Motorcycle;

public class Logistics {
    private List<Vehicle> vehicles;

    public Logistics() {
        this.vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null.");
        }

        this.vehicles.add(vehicle);
        System.out.println("Added a " + vehicle.getClass().getSimpleName() +
                " with " + vehicle.getFuelCapacity() + " fuel capacity.");
    }

    public void displayRanges() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles to display ranges for.");
            return;
        }

        System.out.println("\n--- Vehicle Ranges ---");

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);

            double vehicleRange = vehicle.calculateRange();
            System.out.printf("%d, %s (Fuel Capacity: %.1f units) Max Range: %.2f km%n",
                    i + 1, vehicle.getClass().getSimpleName(),
                    vehicle.getFuelCapacity(), vehicleRange);
        }
        System.out.println("---------------------------");
    }
}
