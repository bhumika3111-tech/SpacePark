
package model;

public class Car extends Vehicle {

    private int numberOfSeats;

    // Default Constructor
    public Car() {
        super();
        this.numberOfSeats = 4;
    }

    // Parameterized Constructor
    public Car(int vehicleId, String registrationNumber,
               String ownerId, String brand, String model,
               int numberOfSeats) {

        super(vehicleId, registrationNumber, ownerId,
              brand, model, "Car");

        if (numberOfSeats <= 0) {
            throw new IllegalArgumentException(
                    "Number of seats must be greater than zero.");
        }

        this.numberOfSeats = numberOfSeats;
    }

    // Getter
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    // Setter
    public void setNumberOfSeats(int numberOfSeats) {

        if (numberOfSeats <= 0) {
            throw new IllegalArgumentException(
                    "Number of seats must be greater than zero.");
        }

        this.numberOfSeats = numberOfSeats;
    }

    // Display Car Details
    @Override
    public void displayVehicleDetails() {

        System.out.println("\n------ CAR DETAILS ------");
        System.out.println("Vehicle ID: " + getVehicleId());
        System.out.println("Registration Number: " + getRegistrationNumber());
        System.out.println("Owner ID: " + getOwnerId());
        System.out.println("Brand: " + getBrand());
        System.out.println("Model: " + getModel());
        System.out.println("Vehicle Type: Car");
        System.out.println("Number of Seats: " + numberOfSeats);
    }
}
