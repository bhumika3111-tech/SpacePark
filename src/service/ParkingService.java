package service;

import exception.InvalidSlotException;
import model.ParkingFloor;
import model.ParkingLot;
import model.ParkingSlot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class ParkingService {
    // Shared default data ensures the leader's no-argument services use the same slots.
    private static final SlotAllocationService SHARED_ALLOCATION_SERVICE = new SlotAllocationService();
    private static final QueueManager SHARED_QUEUE_MANAGER = new QueueManager();
    private static final ParkingLot SHARED_PARKING_LOT = new ParkingLot("SPACEPARK");
    private static final ParkingFloor DEFAULT_FLOOR = new ParkingFloor("Ground Floor");
    private static boolean defaultsLoaded = false;

    private final SlotAllocationService allocationService;

    public ParkingService() {
        this(SHARED_ALLOCATION_SERVICE);
        loadDefaultSlots();
    }

    public ParkingService(SlotAllocationService allocationService) {
        if (allocationService == null) throw new IllegalArgumentException("Allocation service cannot be null.");
        this.allocationService = allocationService;
    }

    private static synchronized void loadDefaultSlots() {
        if (defaultsLoaded) return;
        addDefaultSlot("P01", "Bike");
        addDefaultSlot("P02", "Car");
        addDefaultSlot("P03", "Car");
        addDefaultSlot("P04", "EV");
        addDefaultSlot("P05", "Bike");
        SHARED_PARKING_LOT.addFloor(DEFAULT_FLOOR);
        defaultsLoaded = true;
    }

    private static void addDefaultSlot(String id, String type) {
        ParkingSlot slot = new ParkingSlot(id, type);
        DEFAULT_FLOOR.addSlot(slot);
        SHARED_ALLOCATION_SERVICE.addSlot(slot);
    }

    public static SlotAllocationService getSharedAllocationService() {
        loadDefaultSlots();
        return SHARED_ALLOCATION_SERVICE;
    }

    public static QueueManager getSharedQueueManager() { return SHARED_QUEUE_MANAGER; }

    // Customer menu: Main.java calls this no-argument method.
    public void checkAvailability() {
        loadDefaultSlots();
        System.out.println("===== AVAILABLE PARKING SLOTS =====");
        TreeSet<String> types = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (ParkingSlot slot : allocationService.getAllSlots()) types.add(slot.getSlotType());
        for (String type : types) {
            System.out.println(type + ": " + allocationService.getAvailableSlots(type).size() + " available");
        }
    }

    // Compatibility with the earlier Person 2 test menu.
    public boolean checkAvailability(String vehicleType) {
        return !allocationService.getAvailableSlots(vehicleType).isEmpty();
    }

    public void displayAllSlots() { viewAllSlots(); }

    // Main.java calls this from customer and admin menus.
    public void viewAllSlots() {
        System.out.println("===== ALL PARKING SLOTS =====");
        Iterator<ParkingSlot> iterator = allocationService.getAllSlots().iterator();
        while (iterator.hasNext()) System.out.println(iterator.next());
    }

    public void viewActiveParking() {
        System.out.println("===== ACTIVE PARKING =====");
        boolean found = false;
        for (ParkingSlot slot : allocationService.getAllSlots()) {
            if (!slot.isAvailable() && !slot.isUnderMaintenance()) {
                System.out.println(slot);
                found = true;
            }
        }
        if (!found) System.out.println("No vehicles are currently parked.");
    }

    public void displayAvailableSlots(String vehicleType) {
        System.out.println("Available slots for " + vehicleType + ":");
        for (ParkingSlot slot : allocationService.getAvailableSlots(vehicleType)) System.out.println(slot);
    }

    public void releaseSlot(String slotId) throws InvalidSlotException {
        allocationService.releaseSlot(slotId);
        new BookingService().processWaitingQueue();
    }

    public void addSlot(Scanner sc) {
        System.out.println("===== ADD PARKING SLOT =====");
        System.out.print("Enter new Slot ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Enter slot type (Car/Bike/EV): ");
        String type = sc.nextLine().trim();
        try {
            ParkingSlot slot = new ParkingSlot(id, type);
            allocationService.addSlot(slot);
            System.out.println("Parking slot added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateSlotStatus(Scanner sc) {
        System.out.print("Enter Slot ID: ");
        String id = sc.nextLine().trim();
        System.out.println("1. Available");
        System.out.println("2. Occupied");
        System.out.print("Choose new status: ");
        String choice = sc.nextLine().trim();
        try {
            if (choice.equals("1")) {
                allocationService.updateSlotStatus(id, true);
                System.out.println("Slot marked Available.");
                new BookingService().processWaitingQueue();
            } else if (choice.equals("2")) {
                allocationService.updateSlotStatus(id, false);
                System.out.println("Slot marked Occupied.");
            } else {
                System.out.println("Invalid status choice.");
            }
        } catch (InvalidSlotException e) {
            System.out.println(e.getMessage());
        }
    }

    public void manageMaintenance(Scanner sc) {
        System.out.print("Enter Slot ID: ");
        String id = sc.nextLine().trim();
        System.out.println("1. Mark Under Maintenance");
        System.out.println("2. End Maintenance");
        System.out.print("Choose option: ");
        String choice = sc.nextLine().trim();
        try {
            if (choice.equals("1")) {
                allocationService.setMaintenance(id, true);
                System.out.println("Slot marked Under Maintenance.");
            } else if (choice.equals("2")) {
                allocationService.setMaintenance(id, false);
                System.out.println("Maintenance ended. Slot is now Available.");
                new BookingService().processWaitingQueue();
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (InvalidSlotException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<ParkingSlot> getSlotsSortedById() {
        List<ParkingSlot> sorted = new ArrayList<>(allocationService.getAllSlots());
        Collections.sort(sorted);
        return sorted;
    }

    public List<ParkingSlot> getSlotsSortedByType() {
        List<ParkingSlot> sorted = new ArrayList<>(allocationService.getAllSlots());
        sorted.sort(Comparator.comparing(ParkingSlot::getSlotType, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }
}
