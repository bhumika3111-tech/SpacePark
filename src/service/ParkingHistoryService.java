
package service;

import java.util.ArrayList;
import java.util.Scanner;
import model.ParkingTicket;

public class ParkingHistoryService {

    private ArrayList<ParkingTicket> parkingHistory;

    public ParkingHistoryService() {

        parkingHistory = new ArrayList<>();
    }

    // ADD PARKING RECORD
    public void addParkingRecord(ParkingTicket ticket) {

        if (ticket == null) {
            System.out.println("Invalid parking ticket!");
            return;
        }

        for (ParkingTicket existingTicket : parkingHistory) {

            if (existingTicket.getTicketId().equalsIgnoreCase(
                    ticket.getTicketId())) {

                System.out.println(
                        "This parking ticket already exists in history."
                );
                return;
            }
        }

        parkingHistory.add(ticket);

        System.out.println("Parking record added successfully!");
    }

    // VIEW COMPLETE PARKING HISTORY
    public void viewAllHistory() {

        System.out.println("\n========== PARKING HISTORY ==========");

        if (parkingHistory.isEmpty()) {
            System.out.println("No parking history available.");
            return;
        }

        for (ParkingTicket ticket : parkingHistory) {
            ticket.displayTicketDetails();
        }
    }

    // SEARCH HISTORY BY VEHICLE NUMBER
    public void searchByVehicleNumber(Scanner sc) {

        System.out.println("\n========== SEARCH BY VEHICLE ==========");

        System.out.print("Enter Vehicle Number: ");
        String vehicleNumber = sc.nextLine().trim();

        if (vehicleNumber.isEmpty()) {
            System.out.println("Vehicle number cannot be empty.");
            return;
        }

        boolean found = false;

        for (ParkingTicket ticket : parkingHistory) {

            if (ticket.getVehicleNumber().equalsIgnoreCase(vehicleNumber)) {

                ticket.displayTicketDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    "No parking history found for this vehicle."
            );
        }
    }

    // SEARCH HISTORY BY TICKET ID
    public void searchByTicketId(Scanner sc) {

        System.out.println("\n========== SEARCH BY TICKET ==========");

        System.out.print("Enter Ticket ID: ");
        String ticketId = sc.nextLine().trim();

        if (ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be empty.");
            return;
        }

        for (ParkingTicket ticket : parkingHistory) {

            if (ticket.getTicketId().equalsIgnoreCase(ticketId)) {

                ticket.displayTicketDetails();
                return;
            }
        }

        System.out.println("Parking ticket not found!");
    }

    // SEARCH HISTORY BY SLOT ID
    public void searchBySlotId(Scanner sc) {

        System.out.println("\n========== SEARCH BY PARKING SLOT ==========");

        System.out.print("Enter Slot ID: ");
        String slotId = sc.nextLine().trim();

        if (slotId.isEmpty()) {
            System.out.println("Slot ID cannot be empty.");
            return;
        }

        boolean found = false;

        for (ParkingTicket ticket : parkingHistory) {

            if (ticket.getSlotId().equalsIgnoreCase(slotId)) {

                ticket.displayTicketDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    "No parking records found for this slot."
            );
        }
    }

    // VIEW ACTIVE PARKING RECORDS
    public void viewActiveHistory() {

        System.out.println("\n========== ACTIVE PARKING ==========");

        boolean found = false;

        for (ParkingTicket ticket : parkingHistory) {

            if (ticket.getStatus().equalsIgnoreCase("ACTIVE")) {

                ticket.displayTicketDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No active parking records.");
        }
    }

    // VIEW COMPLETED PARKING RECORDS
    public void viewCompletedHistory() {

        System.out.println("\n========== COMPLETED PARKING ==========");

        boolean found = false;

        for (ParkingTicket ticket : parkingHistory) {

            if (ticket.getStatus().equalsIgnoreCase("COMPLETED")) {

                ticket.displayTicketDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No completed parking records.");
        }
    }

    // GET ALL PARKING HISTORY
    public ArrayList<ParkingTicket> getAllHistory() {

        return new ArrayList<>(parkingHistory);
    }
}
