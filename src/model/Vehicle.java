
package model;

public class Vehicle {

    private int vehicleId;
    private String registrationNumber;
    private String ownerId;
    private String brand;
    private String model;
    private String vehicleType;

    // Default Constructor
    public Vehicle() {
    }

    // Parameterized Constructor
    public Vehicle(int vehicleId, String registrationNumber,
                   String ownerId, String brand,
                   String model, String vehicleType) {

        this.vehicleId = vehicleId;
        this.registrationNumber = registrationNumber;
        this.ownerId = ownerId;
        this.brand = brand;
        this.model = model;
        this.vehicleType = vehicleType;
    }

    // Getter and Setter for Vehicle ID
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    // Getter and Setter for Registration Number
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    // Getter and Setter for Owner ID
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    // Getter and Setter for Brand
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Getter and Setter for Model
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // Getter and Setter for Vehicle Type
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    // Display Vehicle Details
    public void displayVehicleDetails() {

        System.out.println("\n------ VEHICLE DETAILS ------");

        System.out.println("Vehicle ID: " + vehicleId);
        System.out.println("Registration Number: " + registrationNumber);
        System.out.println("Owner ID: " + ownerId);
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Vehicle Type: " + vehicleType);
    }
}
