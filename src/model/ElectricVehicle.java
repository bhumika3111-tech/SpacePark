
package model;

public class ElectricVehicle extends Vehicle {

    private double batteryCapacity;
    private int batteryPercentage;

    // Default Constructor
    public ElectricVehicle() {
        super();
        this.batteryCapacity = 40.0;
        this.batteryPercentage = 100;
    }

    // Parameterized Constructor
    public ElectricVehicle(int vehicleId, String registrationNumber,
                           String ownerId, String brand, String model,
                           double batteryCapacity, int batteryPercentage) {

        super(vehicleId, registrationNumber, ownerId,
              brand, model, "Electric Vehicle");

        if (batteryCapacity <= 0
                || Double.isNaN(batteryCapacity)
                || Double.isInfinite(batteryCapacity)) {

            throw new IllegalArgumentException(
                    "Battery capacity must be greater than zero.");
        }

        if (batteryPercentage < 0 || batteryPercentage > 100) {
            throw new IllegalArgumentException(
                    "Battery percentage must be between 0 and 100.");
        }

        this.batteryCapacity = batteryCapacity;
        this.batteryPercentage = batteryPercentage;
    }

    // Getter and Setter for Battery Capacity
    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(double batteryCapacity) {

        if (batteryCapacity <= 0
                || Double.isNaN(batteryCapacity)
                || Double.isInfinite(batteryCapacity)) {

            throw new IllegalArgumentException(
                    "Battery capacity must be greater than zero.");
        }

        this.batteryCapacity = batteryCapacity;
    }

    // Getter and Setter for Battery Percentage
    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {

        if (batteryPercentage < 0 || batteryPercentage > 100) {
            throw new IllegalArgumentException(
                    "Battery percentage must be between 0 and 100.");
        }

        this.batteryPercentage = batteryPercentage;
    }

    // Display Electric Vehicle Details
    @Override
    public void displayVehicleDetails() {

        System.out.println("\n------ ELECTRIC VEHICLE DETAILS ------");
        System.out.println("Vehicle ID: " + getVehicleId());
        System.out.println("Registration Number: " + getRegistrationNumber());
        System.out.println("Owner ID: " + getOwnerId());
        System.out.println("Brand: " + getBrand());
        System.out.println("Model: " + getModel());
        System.out.println("Vehicle Type: Electric Vehicle");
        System.out.println("Battery Capacity: " + batteryCapacity + " kWh");
        System.out.println("Battery Percentage: " + batteryPercentage + "%");
    }
}
