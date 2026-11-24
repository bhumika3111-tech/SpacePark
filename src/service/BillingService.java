
package service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.ParkingTicket;
import model.Payment;

public class BillingService {

    private static final Map<String, Double> parkingRates =
            new HashMap<>();

    private static final Map<String, Double> payments =
            new HashMap<>();

    private static final Map<String, Payment> paymentRecords =
            new HashMap<>();

    private static int paymentCounter = 1001;

    static {
        parkingRates.put("Car", 30.0);
        parkingRates.put("Bike", 15.0);
        parkingRates.put("EV", 25.0);
    }

    // CALCULATE PARKING FEE
    public void calculateFee(Scanner sc) {

        System.out.println(
                "\n------ PARKING FEE CALCULATION ------"
        );

        System.out.print("Enter vehicle registration number: ");
        String vehicleNumber = sc.nextLine().trim();

        if (vehicleNumber.isEmpty()) {
            System.out.println("Vehicle number cannot be empty.");
            return;
        }

        try {

            ParkingTicket ticket =
                    EntryService.findActiveTicket(vehicleNumber);

            long minutes = Duration.between(
                    ticket.getEntryTime(),
                    LocalDateTime.now()
            ).toMinutes();

            String vehicleType =
                    EntryService.getVehicleType(ticket);

            double fee = calculateParkingFee(
                    vehicleType,
                    minutes
            );

            System.out.println("Vehicle Number: " + vehicleNumber);
            System.out.println(
                    "Parking Duration: " + minutes + " minutes"
            );
            System.out.println(
                    "Estimated Parking Fee: Rs. " + fee
            );

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    // CALCULATE PARKING FEE USING VEHICLE TYPE
    public double calculateParkingFee(
            String vehicleType,
            long minutes) {

        if (vehicleType == null
                || vehicleType.trim().isEmpty()
                || minutes < 0) {

            throw new IllegalArgumentException(
                    "Invalid vehicle type or parking duration."
            );
        }

        Double rate = null;

        for (Map.Entry<String, Double> entry
                : parkingRates.entrySet()) {

            if (entry.getKey().equalsIgnoreCase(vehicleType.trim())) {
                rate = entry.getValue();
                break;
            }
        }

        if (rate == null) {
            throw new IllegalArgumentException(
                    "Parking rate not found for vehicle type."
            );
        }

        long hours = (long) Math.ceil(minutes / 60.0);

        if (hours < 1) {
            hours = 1;
        }

        return hours * rate;
    }

    // UPDATE PARKING RATES
    public void updateRates(Scanner sc) {

        System.out.println("\n------ UPDATE PARKING RATES ------");

        System.out.print("Enter vehicle type (Car/Bike/EV): ");
        String vehicleType = sc.nextLine().trim();

        String matchingType = null;

        for (String type : parkingRates.keySet()) {

            if (type.equalsIgnoreCase(vehicleType)) {
                matchingType = type;
                break;
            }
        }

        if (matchingType == null) {
            System.out.println("Invalid vehicle type.");
            return;
        }

        System.out.print("Enter new hourly rate: ");

        double newRate;

        try {
            newRate = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid amount.");
            return;
        }

        if (newRate <= 0
                || Double.isNaN(newRate)
                || Double.isInfinite(newRate)) {

            System.out.println(
                    "Rate must be a valid amount greater than zero."
            );
            return;
        }

        parkingRates.put(matchingType, newRate);

        System.out.println("Parking rate updated successfully!");
    }

    // PROCESS PAYMENT
    public void processPayment(ParkingTicket ticket, Scanner sc) {

        if (ticket == null || sc == null) {
            System.out.println("Invalid parking ticket or input.");
            return;
        }

        if (ticket.getStatus().equalsIgnoreCase("ACTIVE")) {
            System.out.println(
                    "Vehicle must exit before payment."
            );
            return;
        }

        if (paymentRecords.containsKey(ticket.getTicketId())) {

            System.out.println(
                    "Payment has already been completed for this ticket."
            );

            paymentRecords.get(ticket.getTicketId())
                    .displayPaymentDetails();

            return;
        }

        System.out.println("\n------ PAYMENT ------");

        System.out.println(
                "Parking Fee: Rs. " + ticket.getParkingFee()
        );

        System.out.println("1. Cash");
        System.out.println("2. UPI");
        System.out.println("3. Card");

        System.out.print("Choose payment method: ");

        int choice;

        try {
            choice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice.");
            return;
        }

        String method;

        switch (choice) {

            case 1:
                method = "Cash";
                break;

            case 2:
                method = "UPI";
                break;

            case 3:
                method = "Card";
                break;

            default:
                System.out.println("Invalid payment method.");
                return;
        }

        String paymentId = "PAY" + paymentCounter++;

        Payment payment = new Payment(
                paymentId,
                ticket.getTicketId(),
                ticket.getParkingFee(),
                method
        );

        payment.setPaymentStatus("Successful");

        paymentRecords.put(ticket.getTicketId(), payment);
        payments.put(paymentId, ticket.getParkingFee());

        System.out.println("Payment successful!");
        payment.displayPaymentDetails();
    }

    // GET ALL PAYMENTS
    public Map<String, Double> getPayments() {

        return new HashMap<>(payments);
    }

    // CALCULATE TOTAL REVENUE
    public static double getTotalRevenue() {

        double total = 0;

        for (double amount : payments.values()) {
            total += amount;
        }

        return total;
    }
}
