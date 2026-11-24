
package service;

import java.util.List;
import model.Customer;
import model.ParkingTicket;

public class ReportService {

    // CUSTOMER PARKING HISTORY
    public void viewCustomerHistory(Customer customer) {

        System.out.println(
                "\n------ CUSTOMER PARKING HISTORY ------"
        );

        if (customer == null) {

            System.out.println("Invalid customer.");
            return;
        }

        System.out.println(
                "Customer: " + customer.getName()
        );

        System.out.println(
                "Customer ID: " + customer.getUserId()
        );

        System.out.println(
                "Customer-specific parking history cannot currently "
                        + "be displayed."
        );

        System.out.println(
                "The current parking ticket records do not store "
                        + "the customer ID."
        );

        System.out.println(
                "Customer ownership tracking must be connected "
                        + "to vehicle registration."
        );
    }

    // PARKING REPORT
    public void generateParkingReport() {

        System.out.println("\n========== PARKING REPORT ==========");

        List<ParkingTicket> tickets =
                EntryService.getActiveTickets();

        int totalVehicles = tickets.size();
        int activeVehicles = 0;
        int completedVehicles = 0;

        double totalFees = 0;

        for (ParkingTicket ticket : tickets) {

            if (ticket.getStatus().equalsIgnoreCase("ACTIVE")) {

                activeVehicles++;

            } else if (ticket.getStatus().equalsIgnoreCase("COMPLETED")) {

                completedVehicles++;

                totalFees += ticket.getParkingFee();
            }
        }

        System.out.println(
                "Total Parking Records: " + totalVehicles
        );

        System.out.println(
                "Currently Parked Vehicles: " + activeVehicles
        );

        System.out.println(
                "Completed Parking Sessions: " + completedVehicles
        );

        System.out.println(
                "Total Parking Fees: Rs. " + totalFees
        );
    }

    // REVENUE REPORT
    public void generateRevenueReport() {

        System.out.println("\n========== REVENUE REPORT ==========");

        System.out.println(
                "Total Revenue: Rs. "
                        + BillingService.getTotalRevenue()
        );
    }
}
