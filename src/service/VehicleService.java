
package service;

import java.util.ArrayList;
import java.util.Scanner;

import model.Vehicle;
import model.Customer;
import model.Car;
import model.Bike;
import model.ElectricVehicle;

import exception.DuplicateVehicleException;

public class VehicleService {

    private ArrayList<Vehicle> vehicles;

    public VehicleService() {
        vehicles = new ArrayList<Vehicle>();
    }

    // ADD VEHICLE
    public void addVehicle(Customer customer, Scanner sc) {

        if (customer == null) {
            System.out.println("Please login before adding a vehicle.");
            return;
        }

        System.out.println("\n========== ADD VEHICLE ==========");

        System.out.print("Enter Vehicle Registration Number: ");
        String registrationNumber = sc.nextLine().trim();

        if (registrationNumber.isEmpty()) {
            System.out.println("Registration number cannot be empty.");
            return;
        }

        // Check Duplicate Registration Number
        try {
            checkDuplicateVehicle(registrationNumber);
        }
        catch (DuplicateVehicleException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Enter Vehicle Brand: ");
        String brand = sc.nextLine().trim();

        if (brand.isEmpty()) {
            System.out.println("Brand cannot be empty.");
            return;
        }

        System.out.print("Enter Vehicle Model: ");
        String model = sc.nextLine().trim();

        if (model.isEmpty()) {
            System.out.println("Model cannot be empty.");
            return;
        }

        System.out.println("\nSelect Vehicle Type:");
        System.out.println("1. Car");
        System.out.println("2. Bike");
        System.out.println("3. Electric Vehicle");

        System.out.print("Enter choice: ");

        int choice;

        try {
            choice = Integer.parseInt(sc.nextLine().trim());
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid vehicle type.");
            return;
        }

        // Generate Vehicle ID
        int vehicleId = vehicles.size() + 1001;

        // Owner ID is String
        String ownerId = customer.getUserId();

        Vehicle vehicle;

        try {

            switch (choice) {

                case 1:

                    System.out.print("Enter Number of Seats: ");
                    int seats = Integer.parseInt(sc.nextLine().trim());

                    vehicle = new Car(
                            vehicleId,
                            registrationNumber,
                            ownerId,
                            brand,
                            model,
                            seats
                    );
                    break;

                case 2:

                    System.out.print("Enter Engine Capacity (CC): ");
                    int engineCapacity = Integer.parseInt(sc.nextLine().trim());

                    vehicle = new Bike(
                            vehicleId,
                            registrationNumber,
                            ownerId,
                            brand,
                            model,
                            engineCapacity
                    );
                    break;

                case 3:

                    System.out.print("Enter Battery Capacity (kWh): ");
                    double batteryCapacity =
                            Double.parseDouble(sc.nextLine().trim());

                    System.out.print("Enter Battery Percentage (0-100): ");
                    int batteryPercentage =
                            Integer.parseInt(sc.nextLine().trim());

                    vehicle = new ElectricVehicle(
                            vehicleId,
                            registrationNumber,
                            ownerId,
                            brand,
                            model,
                            batteryCapacity,
                            batteryPercentage
                    );
                    break;

                default:
                    System.out.println("Invalid vehicle type.");
                    return;
            }

            vehicles.add(vehicle);

            System.out.println("\nVehicle added successfully!");

            vehicle.displayVehicleDetails();

        }
        catch (NumberFormatException e) {
            System.out.println("Please enter valid numeric details.");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Invalid vehicle details: " + e.getMessage());
        }
    }

    // CHECK DUPLICATE VEHICLE
    public void checkDuplicateVehicle(String registrationNumber)
            throws DuplicateVehicleException {

        if (registrationNumber == null
                || registrationNumber.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Registration number cannot be empty.");
        }

        for (Vehicle vehicle : vehicles) {

            if (vehicle.getRegistrationNumber()
                    .equalsIgnoreCase(registrationNumber.trim())) {

                throw new DuplicateVehicleException(
                        "This vehicle is already registered!"
                );
            }
        }
    }

    // VIEW CUSTOMER VEHICLES
    public void viewVehicles(Customer customer) {

        if (customer == null) {
            System.out.println("Customer information not found.");
            return;
        }

        System.out.println("\n========== MY VEHICLES ==========");

        boolean found = false;

        for (Vehicle vehicle : vehicles) {

            if (vehicle.getOwnerId()
                    .equalsIgnoreCase(customer.getUserId())) {

                vehicle.displayVehicleDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No vehicles registered yet.");
        }
    }

    // VIEW ALL REGISTERED VEHICLES
    public void viewAllVehicles() {

        System.out.println("\n========== ALL REGISTERED VEHICLES ==========");

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles registered.");
            return;
        }

        for (Vehicle vehicle : vehicles) {
            vehicle.displayVehicleDetails();
        }
    }

    // GET ALL VEHICLES
    public ArrayList<Vehicle> getAllVehicles() {
        return new ArrayList<Vehicle>(vehicles);
    }
}
