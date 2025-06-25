package vehicles;

import vehicles.vehicleTypes.Car;
import vehicles.vehicleTypes.Truck;
import vehicles.vehicleTypes.Motorcycle;

public class Main {
    public static void main(String[] args) {
        // Creating instances of Car, Truck, and Motorcycle with different fuel
        // capacities.
        Car car1 = new Car(500.0);
        Truck truck1 = new Truck(1000.0);
        Motorcycle motocycle1 = new Motorcycle(150);
        Car car2 = new Car(750.0);
        Truck truck2 = new Truck(1200.0);

        Logistics logisticsCompany = new Logistics();

        // Adding vehicles to the logistics object
        logisticsCompany.addVehicle(car1);
        logisticsCompany.addVehicle(truck1);
        logisticsCompany.addVehicle(motocycle1);
        logisticsCompany.addVehicle(car2);
        logisticsCompany.addVehicle(truck2);

        // Calling displayRanges() to demonstrate polymorphism
        logisticsCompany.displayRanges();

    }

}
