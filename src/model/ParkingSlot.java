
package model;

public class ParkingSlot implements Comparable<ParkingSlot> {

    private String slotId;
    private String slotType;
    private boolean available;
    private boolean underMaintenance;

    // Parameterized Constructor
    public ParkingSlot(String slotId, String slotType) {

        this.slotId = slotId;
        this.slotType = slotType;
        this.available = true;
        this.underMaintenance = false;
    }

    // Getters
    public String getSlotId() {
        return slotId;
    }

    public String getSlotType() {
        return slotType;
    }

    public boolean isAvailable() {
        return available && !underMaintenance;
    }

    public boolean isUnderMaintenance() {
        return underMaintenance;
    }

    // Setters
    public void setAvailable(boolean available) {

        if (!underMaintenance) {
            this.available = available;
        }
    }

    public void setUnderMaintenance(boolean underMaintenance) {

        this.underMaintenance = underMaintenance;

        if (underMaintenance) {
            this.available = false;
        }
    }

    // Compare Parking Slots
    @Override
    public int compareTo(ParkingSlot other) {

        return this.slotId.compareTo(other.slotId);
    }

    // Display Parking Slot Details
    @Override
    public String toString() {

        String status;

        if (underMaintenance) {
            status = "Under Maintenance";
        }
        else if (available) {
            status = "Available";
        }
        else {
            status = "Occupied";
        }

        return "Slot ID: " + slotId
                + " | Type: " + slotType
                + " | Status: " + status;
    }
}
