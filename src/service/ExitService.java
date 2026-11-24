
package service;

import exception.InvalidTicketException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;
import model.ParkingTicket;

public class ExitService {

    private final ParkingService parkingService;
    private final BillingService billingService;

    public ExitService() {
        parkingService = new ParkingService();
        billingService = new BillingService();
    }

    public void recordExit(Scanner sc) {

        System.out.println("\n------ VEHICLE EXIT ------");

        System.out.print("Enter vehicle registration number: ");
        String vehicleNumber = sc.nextLine().trim();

        if (vehicleNumber.isEmpty()) {
            System.out.println("Vehicle number cannot be empty.");
            return;
        }

        try {

            ParkingTicket ticket =
                    EntryService.findActiveTicket(vehicleNumber);

            LocalDateTime exitTime = LocalDateTime.now();

            long minutes = Duration.between(
                    ticket.getEntryTime(),
                    exitTime
            ).toMinutes();

            if (minutes < 0) {
                System.out.println("Invalid parking time.");
                return;
            }

            String vehicleType =
                    EntryService.getVehicleType(ticket);

            if (vehicleType == null) {
                System.out.println(
                        "Unable to identify the vehicle type."
                );
                return;
            }

            double fee = billingService.calculateParkingFee(
                    vehicleType,
                    minutes
            );

            // Release the slot before completing the ticket
            parkingService.releaseSlot(ticket.getSlotId());

            // Update ticket only after successful slot release
            ticket.setExitTime(exitTime);
            ticket.setParkingFee(fee);
            ticket.setStatus("COMPLETED");

            System.out.println(
                    "\nVehicle exit recorded successfully!"
            );

            System.out.println("Ticket ID: " + ticket.getTicketId());
            System.out.println(
                    "Vehicle Number: " + ticket.getVehicleNumber()
            );
            System.out.println("Slot ID: " + ticket.getSlotId());
            System.out.println("Entry Time: " + ticket.getEntryTime());
            System.out.println("Exit Time: " + ticket.getExitTime());
            System.out.println(
                    "Parking Duration: " + minutes + " minutes"
            );
            System.out.println("Parking Fee: Rs. " + fee);

            System.out.println(
                    "\nPlease proceed to payment."
            );

        } catch (InvalidTicketException e) {

            System.out.println(e.getMessage());

        } catch (Exception e) {

            System.out.println(
                    "Unable to record vehicle exit: " + e.getMessage()
            );
        }
    }
}
