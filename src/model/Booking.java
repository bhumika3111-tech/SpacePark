
package model;

import java.time.LocalDateTime;

public class Booking {

    private String bookingId;
    private String vehicleNumber;
    private String vehicleType;
    private String slotId;
    private LocalDateTime bookingTime;
    private String status;

    // Parameterized Constructor
    public Booking(String bookingId, String vehicleNumber,
                   String vehicleType) {

        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Booking ID cannot be empty.");
        }

        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Vehicle number cannot be empty.");
        }

        if (vehicleType == null || vehicleType.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Vehicle type cannot be empty.");
        }

        this.bookingId = bookingId;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.slotId = "NOT_ASSIGNED";
        this.bookingTime = LocalDateTime.now();
        this.status = "PENDING";
    }

    // Getters
    public String getBookingId() {
        return bookingId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getSlotId() {
        return slotId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Display Booking Details
    @Override
    public String toString() {

        return "Booking ID: " + bookingId
                + " | Vehicle: " + vehicleNumber
                + " | Type: " + vehicleType
                + " | Slot: " + slotId
                + " | Status: " + status;
    }
}
