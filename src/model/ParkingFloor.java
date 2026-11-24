
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParkingFloor {

    private String floorId;
    private List<ParkingSlot> slots;

    // Constructor
    public ParkingFloor(String floorId) {
        this.floorId = floorId;
        this.slots = new ArrayList<>();
    }

    // Getter
    public String getFloorId() {
        return floorId;
    }

    // Add Parking Slot
    public void addSlot(ParkingSlot slot) {

        if (slot == null) {
            throw new IllegalArgumentException(
                    "Parking slot cannot be null.");
        }

        for (ParkingSlot existingSlot : slots) {

            if (existingSlot.getSlotId().equalsIgnoreCase(
                    slot.getSlotId())) {

                throw new IllegalArgumentException(
                        "Duplicate slot ID: " + slot.getSlotId());
            }
        }

        slots.add(slot);
    }

    // Get All Slots
    public List<ParkingSlot> getSlots() {
        return Collections.unmodifiableList(slots);
    }

    // Display Floor Slots
    public void displaySlots() {

        System.out.println("\nFloor: " + floorId);

        if (slots.isEmpty()) {
            System.out.println("No parking slots available.");
            return;
        }

        for (ParkingSlot slot : slots) {
            System.out.println(slot);
        }
    }
}
