
package service;

import exception.InvalidSlotException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.ParkingSlot;

public class SlotAllocationService {

    private final List<ParkingSlot> slots;
    private final Set<String> uniqueSlotIds;
    private final Map<String, ParkingSlot> slotMap;

    public SlotAllocationService() {
        slots = new ArrayList<>();
        uniqueSlotIds = new HashSet<>();
        slotMap = new HashMap<>();
    }

    // ADD PARKING SLOT
    public synchronized void addSlot(ParkingSlot slot) {

        if (slot == null || slot.getSlotId() == null
                || slot.getSlotId().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Slot and slot ID cannot be empty.");
        }

        if (slot.getSlotType() == null
                || slot.getSlotType().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Slot type cannot be empty.");
        }

        String normalizedId = normalizeId(slot.getSlotId());

        if (!uniqueSlotIds.add(normalizedId)) {
            throw new IllegalArgumentException(
                    "Duplicate slot ID: " + slot.getSlotId());
        }

        slots.add(slot);
        slotMap.put(normalizedId, slot);
    }

    // ALLOCATE SLOT BY VEHICLE TYPE
    public synchronized ParkingSlot allocateSlot(String vehicleType) {

        if (vehicleType == null || vehicleType.trim().isEmpty()) {
            return null;
        }

        for (ParkingSlot slot : slots) {

            if (slot.isAvailable()
                    && sameVehicleType(slot.getSlotType(), vehicleType)) {

                slot.setAvailable(false);
                return slot;
            }
        }

        return null;
    }

    // ALLOCATE SPECIFIC SLOT
    public synchronized boolean allocateSlot(ParkingSlot slot) {

        if (slot == null) {
            return false;
        }

        ParkingSlot stored = slotMap.get(
                normalizeId(slot.getSlotId()));

        if (stored != null && stored.isAvailable()) {
            stored.setAvailable(false);
            return true;
        }

        return false;
    }

    // RELEASE SLOT
    public synchronized void releaseSlot(String slotId)
            throws InvalidSlotException {

        ParkingSlot slot = findSlot(slotId);

        if (slot.isUnderMaintenance()) {
            throw new InvalidSlotException(
                    "Slot is under maintenance and cannot be released for use.");
        }

        slot.setAvailable(true);
    }

    // UPDATE SLOT STATUS
    public synchronized void updateSlotStatus(
            String slotId, boolean available)
            throws InvalidSlotException {

        ParkingSlot slot = findSlot(slotId);

        if (slot.isUnderMaintenance() && available) {
            throw new InvalidSlotException(
                    "End maintenance before making this slot available.");
        }

        slot.setAvailable(available);
    }

    // SET MAINTENANCE STATUS
    public synchronized void setMaintenance(
            String slotId, boolean maintenance)
            throws InvalidSlotException {

        ParkingSlot slot = findSlot(slotId);

        if (maintenance) {

            if (!slot.isAvailable()) {
                throw new InvalidSlotException(
                        "An occupied slot cannot be marked for maintenance.");
            }

            slot.setUnderMaintenance(true);

        } else {

            if (!slot.isUnderMaintenance()) {
                throw new InvalidSlotException(
                        "This slot is not under maintenance.");
            }

            slot.setUnderMaintenance(false);
            slot.setAvailable(true);
        }
    }

    // FIND SLOT
    public synchronized ParkingSlot findSlot(String slotId)
            throws InvalidSlotException {

        ParkingSlot slot = slotMap.get(normalizeId(slotId));

        if (slot == null) {
            throw new InvalidSlotException(
                    "Invalid slot ID: " + slotId);
        }

        return slot;
    }

    // GET ALL SLOTS
    public List<ParkingSlot> getAllSlots() {
        return Collections.unmodifiableList(slots);
    }

    // GET AVAILABLE SLOTS BY TYPE
    public List<ParkingSlot> getAvailableSlots(String vehicleType) {

        List<ParkingSlot> result = new ArrayList<>();

        for (ParkingSlot slot : slots) {

            if (slot.isAvailable()
                    && sameVehicleType(slot.getSlotType(), vehicleType)) {

                result.add(slot);
            }
        }

        return result;
    }

    // GET SLOT MAP
    public Map<String, ParkingSlot> getSlotMap() {
        return Collections.unmodifiableMap(slotMap);
    }

    // NORMALIZE SLOT ID
    private String normalizeId(String id) {
        return id == null ? "" : id.trim().toUpperCase();
    }

    // COMPARE VEHICLE TYPES
    private boolean sameVehicleType(String a, String b) {
        return normalizeType(a).equals(normalizeType(b));
    }

    // NORMALIZE VEHICLE TYPE
    private String normalizeType(String type) {

        if (type == null) {
            return "";
        }

        String value = type.trim().toLowerCase()
                .replace(" ", "")
                .replace("-", "");

        if (value.equals("electricvehicle")
                || value.equals("electric")
                || value.equals("ev")) {
            return "ev";
        }

        return value;
    }
}
