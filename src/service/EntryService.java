
package service;

import exception.InvalidTicketException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import model.ParkingSlot;
import model.ParkingTicket;

public class EntryService {

    private static final List<ParkingTicket> activeTickets =
            new ArrayList<>();

    private static final Map<String, String> ticketVehicleTypes =
            new HashMap<>();

    private static int ticketCounter = 1001;

    private final SlotAllocationService allocationService;

    public EntryService() {
        allocationService =
                ParkingService.getSharedAllocationService();
    }

    public void recordEntry(Scanner sc) {

        System.out.println("\n------ VEHICLE ENTRY ------");

        System.out.print("Enter vehicle registration number: ");
        String vehicleNumber = sc.nextLine().trim();

        if (vehicleNumber.isEmpty()) {
            System.out.println("Vehicle number cannot be empty.");
            return;
        }

        for (ParkingTicket ticket : activeTickets) {

            if (ticket.getVehicleNumber().equalsIgnoreCase(vehicleNumber)
                    && ticket.getStatus().equalsIgnoreCase("ACTIVE")) {

                System.out.println("This vehicle is already parked.");
                return;
            }
        }

        System.out.print("Enter vehicle type (Car/Bike/EV): ");
        String vehicleType = sc.nextLine().trim();

        if (!vehicleType.equalsIgnoreCase("Car")
                && !vehicleType.equalsIgnoreCase("Bike")
                && !vehicleType.equalsIgnoreCase("EV")) {

            System.out.println("Invalid vehicle type.");
            return;
        }

        // Get available slots matching the vehicle type
        List<ParkingSlot> matchingSlots =
                allocationService.getAvailableSlots(vehicleType);

        if (matchingSlots.isEmpty()) {
            System.out.println(
                    "No parking slot available for this vehicle."
            );
            return;
        }

        System.out.println("\nAvailable Slots:");

        for (ParkingSlot slot : matchingSlots) {

            System.out.println(
                    slot.getSlotId() + " - " + slot.getSlotType()
            );
        }

        System.out.print("Enter slot ID: ");
        String slotId = sc.nextLine().trim();

        ParkingSlot selectedSlot = null;

        for (ParkingSlot slot : matchingSlots) {

            if (slot.getSlotId().equalsIgnoreCase(slotId)) {
                selectedSlot = slot;
                break;
            }
        }

        if (selectedSlot == null) {
            System.out.println("Invalid or unavailable slot.");
            return;
        }

        try {

            // allocateSlot returns boolean
            boolean allocated =
                    allocationService.allocateSlot(selectedSlot);

            if (!allocated) {
                System.out.println(
                        "This slot is no longer available."
                );
                return;
            }

            String ticketId = "SP" + ticketCounter++;

            ParkingTicket ticket = new ParkingTicket(
                    ticketId,
                    vehicleNumber,
                    selectedSlot.getSlotId(),
                    LocalDateTime.now()
            );

            activeTickets.add(ticket);
            ticketVehicleTypes.put(ticketId, vehicleType);

            System.out.println(
                    "\nVehicle entry recorded successfully!"
            );

            ticket.displayTicketDetails();

        } catch (Exception e) {

            System.out.println(
                    "Unable to record vehicle entry: " + e.getMessage()
            );
        }
    }

    // GET ALL PARKING TICKETS
    public static List<ParkingTicket> getActiveTickets() {

        return new ArrayList<>(activeTickets);
    }

    // FIND ACTIVE TICKET
    public static ParkingTicket findActiveTicket(String vehicleNumber)
            throws InvalidTicketException {

        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {

            throw new InvalidTicketException(
                    "Vehicle number cannot be empty."
            );
        }

        for (ParkingTicket ticket : activeTickets) {

            if (ticket.getVehicleNumber().equalsIgnoreCase(
                    vehicleNumber.trim())
                    && ticket.getStatus().equalsIgnoreCase("ACTIVE")) {

                return ticket;
            }
        }

        throw new InvalidTicketException(
                "No active parking ticket found."
        );
    }

    // GET VEHICLE TYPE USING TICKET
    public static String getVehicleType(ParkingTicket ticket) {

        if (ticket == null) {
            return null;
        }

        return ticketVehicleTypes.get(ticket.getTicketId());
    }
}
