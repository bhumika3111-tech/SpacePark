
package model;

import java.time.LocalDateTime;

public class ChargingSession {

    private String sessionId;
    private String stationId;
    private String vehicleNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double energyConsumed;
    private double chargingCost;
    private String status;

    public ChargingSession() {
        this.energyConsumed = 0.0;
        this.chargingCost = 0.0;
        this.status = "ACTIVE";
    }

    public ChargingSession(String sessionId, String stationId,
            String vehicleNumber, LocalDateTime startTime) {

        setSessionId(sessionId);
        setStationId(stationId);
        setVehicleNumber(vehicleNumber);
        setStartTime(startTime);

        this.endTime = null;
        this.energyConsumed = 0.0;
        this.chargingCost = 0.0;
        this.status = "ACTIVE";
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Session ID cannot be empty.");
        }
        this.sessionId = sessionId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        if (stationId == null || stationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Station ID cannot be empty.");
        }
        this.stationId = stationId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle number cannot be empty.");
        }
        this.vehicleNumber = vehicleNumber;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("Start time cannot be null.");
        }
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        if (endTime != null && startTime != null
                && endTime.isBefore(startTime)) {
            throw new IllegalArgumentException(
                    "End time cannot be before start time.");
        }
        this.endTime = endTime;
    }

    public double getEnergyConsumed() {
        return energyConsumed;
    }

    public void setEnergyConsumed(double energyConsumed) {
        if (energyConsumed < 0 || Double.isNaN(energyConsumed)
                || Double.isInfinite(energyConsumed)) {
            throw new IllegalArgumentException("Invalid energy consumed.");
        }
        this.energyConsumed = energyConsumed;
    }

    public double getChargingCost() {
        return chargingCost;
    }

    public void setChargingCost(double chargingCost) {
        if (chargingCost < 0 || Double.isNaN(chargingCost)
                || Double.isInfinite(chargingCost)) {
            throw new IllegalArgumentException("Invalid charging cost.");
        }
        this.chargingCost = chargingCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty.");
        }
        this.status = status;
    }

    public void displaySessionDetails() {
        System.out.println("\n----- CHARGING SESSION -----");
        System.out.println("Session ID: " + sessionId);
        System.out.println("Station ID: " + stationId);
        System.out.println("Vehicle Number: " + vehicleNumber);
        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
        System.out.println("Energy Consumed: " + energyConsumed + " kWh");
        System.out.println("Charging Cost: Rs. " + chargingCost);
        System.out.println("Status: " + status);
    }
}
