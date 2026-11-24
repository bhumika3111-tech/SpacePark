
package model;

import java.time.LocalDateTime;

public class Payment {

    private String paymentId;
    private String ticketId;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;
    private LocalDateTime paymentTime;

    // Parameterized Constructor
    public Payment(String paymentId, String ticketId, double amount,
                   String paymentMethod) {

        if (paymentId == null || paymentId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Payment ID cannot be empty.");
        }

        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Ticket ID cannot be empty.");
        }

        if (amount < 0 || Double.isNaN(amount)
                || Double.isInfinite(amount)) {
            throw new IllegalArgumentException(
                    "Invalid payment amount.");
        }

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Payment method cannot be empty.");
        }

        this.paymentId = paymentId;
        this.ticketId = ticketId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "Pending";
        this.paymentTime = LocalDateTime.now();
    }

    // Getters
    public String getPaymentId() {
        return paymentId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    // Setter
    public void setPaymentStatus(String paymentStatus) {

        if (paymentStatus == null || paymentStatus.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Payment status cannot be empty.");
        }

        this.paymentStatus = paymentStatus;
    }

    // Display Payment Details
    public void displayPaymentDetails() {

        System.out.println("\n------ PAYMENT DETAILS ------");
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Amount: Rs. " + amount);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Payment Status: " + paymentStatus);
        System.out.println("Payment Time: " + paymentTime);
    }
}
