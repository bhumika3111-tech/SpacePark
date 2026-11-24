
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParkingLot {

    private String parkingLotName;
    private List<ParkingFloor> floors;

    // Constructor
    public ParkingLot(String parkingLotName) {

        this.parkingLotName = parkingLotName;
        this.floors = new ArrayList<>();
    }

    // Getter
    public String getParkingLotName() {
        return parkingLotName;
    }

    // Add Parking Floor
    public void addFloor(ParkingFloor floor) {

        if (floor == null) {
            throw new IllegalArgumentException(
                    "Parking floor cannot be null.");
        }

        for (ParkingFloor existingFloor : floors) {

            if (existingFloor.getFloorId().equalsIgnoreCase(
                    floor.getFloorId())) {

                throw new IllegalArgumentException(
                        "Duplicate floor ID: " + floor.getFloorId());
            }
        }

        floors.add(floor);
    }

    // Get All Floors
    public List<ParkingFloor> getFloors() {

        return Collections.unmodifiableList(floors);
    }

    // Get All Parking Slots
    public List<ParkingSlot> getAllSlots() {

        List<ParkingSlot> allSlots = new ArrayList<>();

        for (ParkingFloor floor : floors) {
            allSlots.addAll(floor.getSlots());
        }

        return allSlots;
    }

    // Display Parking Lot
    public void displayParkingLot() {

        System.out.println("\n===== " + parkingLotName + " =====");

        if (floors.isEmpty()) {
            System.out.println("No parking floors available.");
            return;
        }

        for (ParkingFloor floor : floors) {
            floor.displaySlots();
        }
    }
}
