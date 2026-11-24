
package model;

import java.time.LocalDateTime;

public class MaintenanceRecord {

    private String recordId;
    private String slotId;
    private String description;
    private LocalDateTime maintenanceDate;
    private String status;
    private double maintenanceCost;

    public MaintenanceRecord() {
        this.status = "PENDING";
        this.maintenanceCost = 0.0;
    }

    public MaintenanceRecord(String recordId, String slotId,
            String description, LocalDateTime maintenanceDate) {

        setRecordId(recordId);
        setSlotId(slotId);
        setDescription(description);
        setMaintenanceDate(maintenanceDate);

        this.status = "PENDING";
        this.maintenanceCost = 0.0;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        if (recordId == null || recordId.trim().isEmpty()) {
            throw new IllegalArgumentException("Record ID cannot be empty.");
        }
        this.recordId = recordId;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        if (slotId == null || slotId.trim().isEmpty()) {
            throw new IllegalArgumentException("Slot ID cannot be empty.");
        }
        this.slotId = slotId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Description cannot be empty.");
        }
        this.description = description;
    }

    public LocalDateTime getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDateTime maintenanceDate) {
        if (maintenanceDate == null) {
            throw new IllegalArgumentException(
                    "Maintenance date cannot be null.");
        }
        this.maintenanceDate = maintenanceDate;
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

    public double getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(double maintenanceCost) {
        if (maintenanceCost < 0 || Double.isNaN(maintenanceCost)
                || Double.isInfinite(maintenanceCost)) {
            throw new IllegalArgumentException(
                    "Invalid maintenance cost.");
        }
        this.maintenanceCost = maintenanceCost;
    }

    public void displayMaintenanceDetails() {
        System.out.println("\n----- MAINTENANCE RECORD -----");
        System.out.println("Record ID: " + recordId);
        System.out.println("Slot ID: " + slotId);
        System.out.println("Description: " + description);
        System.out.println("Maintenance Date: " + maintenanceDate);
        System.out.println("Status: " + status);
        System.out.println("Maintenance Cost: Rs. " + maintenanceCost);
    }
}
