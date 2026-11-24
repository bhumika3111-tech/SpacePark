
package model;

import java.time.LocalDateTime;

public class ParkingTicket {

    private String ticketId;
    private String vehicleNumber;
    private String slotId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double parkingFee;
    private String status;

    // Default Constructor
    public ParkingTicket() {
    }

    // Parameterized Constructor
    public ParkingTicket(String ticketId, String vehicleNumber,
                         String slotId, LocalDateTime entryTime) {

        if (ticketId == null || ticketId.trim().isEmpty()
                || vehicleNumber == null || vehicleNumber.trim().isEmpty()
                || slotId == null || slotId.trim().isEmpty()
                || entryTime == null) {

            throw new IllegalArgumentException(
                    "Invalid parking ticket details.");
        }

        this.ticketId = ticketId;
        this.vehicleNumber = vehicleNumber;
        this.slotId = slotId;
        this.entryTime = entryTime;
        this.exitTime = null;
        this.parkingFee = 0.0;
        this.status = "ACTIVE";
    }

    // Getters and Setters
    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public double getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(double parkingFee) {

        if (parkingFee < 0 || Double.isNaN(parkingFee)
                || Double.isInfinite(parkingFee)) {

            throw new IllegalArgumentException(
                    "Parking fee cannot be negative or invalid.");
        }

        this.parkingFee = parkingFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Display Ticket Details
    public void displayTicketDetails() {

        System.out.println("\n----- PARKING TICKET -----");
        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Vehicle Number: " + vehicleNumber);
        System.out.println("Slot ID: " + slotId);
        System.out.println("Entry Time: " + entryTime);
        System.out.println("Exit Time: " + exitTime);
        System.out.println("Parking Fee: Rs. " + parkingFee);
        System.out.println("Status: " + status);
    }
}
