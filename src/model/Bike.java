
package model;

public class Bike extends Vehicle {

    private int engineCapacity;

    // Default Constructor
    public Bike() {
        super();
        this.engineCapacity = 100;
    }

    // Parameterized Constructor
    public Bike(int vehicleId, String registrationNumber,
                String ownerId, String brand, String model,
                int engineCapacity) {

        super(vehicleId, registrationNumber, ownerId,
              brand, model, "Bike");

        if (engineCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Engine capacity must be greater than zero.");
        }

        this.engineCapacity = engineCapacity;
    }

    // Getter
    public int getEngineCapacity() {
        return engineCapacity;
    }

    // Setter
    public void setEngineCapacity(int engineCapacity) {

        if (engineCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Engine capacity must be greater than zero.");
        }

        this.engineCapacity = engineCapacity;
    }

    // Display Bike Details
    @Override
    public void displayVehicleDetails() {

        System.out.println("\n------ BIKE DETAILS ------");
        System.out.println("Vehicle ID: " + getVehicleId());
        System.out.println("Registration Number: " + getRegistrationNumber());
        System.out.println("Owner ID: " + getOwnerId());
        System.out.println("Brand: " + getBrand());
        System.out.println("Model: " + getModel());
        System.out.println("Vehicle Type: Bike");
        System.out.println("Engine Capacity: " + engineCapacity + " CC");
    }
}
