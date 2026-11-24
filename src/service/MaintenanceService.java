
package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import model.MaintenanceRecord;

public class MaintenanceService {

    private ArrayList<MaintenanceRecord> maintenanceRecords;

    private int recordCounter;

    private final SlotAllocationService allocationService;

    public MaintenanceService() {

        maintenanceRecords = new ArrayList<>();
        recordCounter = 1;

        allocationService =
                ParkingService.getSharedAllocationService();
    }

    public void manageMaintenance(Scanner sc) {

        boolean active = true;

        while (active) {

            System.out.println("\n========== MAINTENANCE MENU ==========");
            System.out.println("1. Add Maintenance Record");
            System.out.println("2. View All Maintenance Records");
            System.out.println("3. Search Maintenance Record");
            System.out.println("4. Update Maintenance Status");
            System.out.println("5. Update Maintenance Cost");
            System.out.println("6. Back to Admin Menu");

            System.out.print("Enter choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                continue;
            }

            switch (choice) {

                case 1:
                    addMaintenanceRecord(sc);
                    break;

                case 2:
                    viewAllMaintenanceRecords();
                    break;

                case 3:
                    searchMaintenanceRecord(sc);
                    break;

                case 4:
                    updateMaintenanceStatus(sc);
                    break;

                case 5:
                    updateMaintenanceCost(sc);
                    break;

                case 6:
                    active = false;
                    System.out.println("Returning to Admin Menu.");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public void addMaintenanceRecord(Scanner sc) {

        System.out.println("\n========== ADD MAINTENANCE ==========");

        System.out.print("Enter Parking Slot ID: ");
        String slotId = sc.nextLine().trim();

        if (slotId.isEmpty()) {
            System.out.println("Slot ID cannot be empty!");
            return;
        }

        System.out.print("Enter Maintenance Description: ");
        String description = sc.nextLine().trim();

        if (description.isEmpty()) {
            System.out.println("Description cannot be empty!");
            return;
        }

        for (MaintenanceRecord record : maintenanceRecords) {

            if (record.getSlotId().equalsIgnoreCase(slotId)
                    && !record.getStatus().equalsIgnoreCase("COMPLETED")) {

                System.out.println(
                        "This slot already has an active maintenance record."
                );
                return;
            }
        }

        try {

            allocationService.setMaintenance(slotId, true);

            String recordId = "MR" + recordCounter;

            MaintenanceRecord record = new MaintenanceRecord(
                    recordId,
                    slotId,
                    description,
                    LocalDateTime.now()
            );

            maintenanceRecords.add(record);
            recordCounter++;

            System.out.println(
                    "\nMaintenance Record Added Successfully!"
            );

            record.displayMaintenanceDetails();

        } catch (Exception e) {

            System.out.println(
                    "Unable to add maintenance record: " + e.getMessage()
            );
        }
    }

    public void viewAllMaintenanceRecords() {

        System.out.println("\n========== MAINTENANCE RECORDS ==========");

        if (maintenanceRecords.isEmpty()) {
            System.out.println("No maintenance records found.");
            return;
        }

        for (MaintenanceRecord record : maintenanceRecords) {
            record.displayMaintenanceDetails();
        }
    }

    public void searchMaintenanceRecord(Scanner sc) {

        System.out.println("\n========== SEARCH MAINTENANCE ==========");

        System.out.print("Enter Record ID: ");
        String recordId = sc.nextLine().trim();

        for (MaintenanceRecord record : maintenanceRecords) {

            if (record.getRecordId().equalsIgnoreCase(recordId)) {
                record.displayMaintenanceDetails();
                return;
            }
        }

        System.out.println("Maintenance record not found!");
    }

    public void updateMaintenanceStatus(Scanner sc) {

        System.out.println("\n========== UPDATE STATUS ==========");

        System.out.print("Enter Record ID: ");
        String recordId = sc.nextLine().trim();

        MaintenanceRecord selectedRecord = null;

        for (MaintenanceRecord record : maintenanceRecords) {

            if (record.getRecordId().equalsIgnoreCase(recordId)) {
                selectedRecord = record;
                break;
            }
        }

        if (selectedRecord == null) {
            System.out.println("Maintenance record not found!");
            return;
        }

        if (selectedRecord.getStatus().equalsIgnoreCase("COMPLETED")) {
            System.out.println(
                    "This maintenance record is already completed."
            );
            return;
        }

        System.out.println("\nSelect New Status:");
        System.out.println("1. PENDING");
        System.out.println("2. IN_PROGRESS");
        System.out.println("3. COMPLETED");

        System.out.print("Enter choice: ");

        int choice;

        try {
            choice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
            return;
        }

        String status;

        switch (choice) {

            case 1:
                status = "PENDING";
                break;

            case 2:
                status = "IN_PROGRESS";
                break;

            case 3:
                status = "COMPLETED";
                break;

            default:
                System.out.println("Invalid status choice!");
                return;
        }

        try {

            if (status.equals("COMPLETED")) {

                allocationService.setMaintenance(
                        selectedRecord.getSlotId(),
                        false
                );
            }

            selectedRecord.setStatus(status);

            System.out.println(
                    "Maintenance Status Updated Successfully!"
            );

            if (status.equals("COMPLETED")) {
                System.out.println(
                        "The parking slot is available again."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to update maintenance: " + e.getMessage()
            );
        }
    }

    public void updateMaintenanceCost(Scanner sc) {

        System.out.println(
                "\n========== UPDATE MAINTENANCE COST =========="
        );

        System.out.print("Enter Record ID: ");
        String recordId = sc.nextLine().trim();

        MaintenanceRecord selectedRecord = null;

        for (MaintenanceRecord record : maintenanceRecords) {

            if (record.getRecordId().equalsIgnoreCase(recordId)) {
                selectedRecord = record;
                break;
            }
        }

        if (selectedRecord == null) {
            System.out.println("Maintenance record not found!");
            return;
        }

        System.out.print("Enter Maintenance Cost: Rs. ");

        double cost;

        try {
            cost = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid cost!");
            return;
        }

        if (cost < 0
                || Double.isNaN(cost)
                || Double.isInfinite(cost)) {

            System.out.println("Enter a valid non-negative cost!");
            return;
        }

        selectedRecord.setMaintenanceCost(cost);

        System.out.println(
                "Maintenance Cost Updated Successfully!"
        );

        System.out.println("Updated Cost: Rs. " + cost);
    }

    public ArrayList<MaintenanceRecord> getAllMaintenanceRecords() {
        return new ArrayList<>(maintenanceRecords);
    }
}
