
package main;

import java.util.Scanner;

// Model classes
import model.User;
import model.Customer;
import model.Admin;
import model.Operator;

// Service classes
import service.AuthenticationService;
import service.VehicleService;
import service.ParkingService;
import service.BookingService;
import service.EntryService;
import service.ExitService;
import service.BillingService;
import service.ReportService;
import service.ChargingService;

// Exception classes
import exception.InvalidUserException;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    // Service objects shared across the application
    private static final AuthenticationService auth =
            new AuthenticationService();

    private static final VehicleService vehicleService =
            new VehicleService();

    private static final ParkingService parkingService =
            new ParkingService();

    private static final BookingService bookingService =
            new BookingService();

    private static final EntryService entryService =
            new EntryService();

    private static final ExitService exitService =
            new ExitService();

    private static final BillingService billingService =
            new BillingService();

    private static final ReportService reportService =
            new ReportService();

    private static final ChargingService chargingService =
            new ChargingService();

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println("       WELCOME TO SPACEPARK       ");
        System.out.println(" Smart Parking & EV Management    ");
        System.out.println("==================================");

        boolean running = true;

        while (running) {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");

            int choice = readChoice();

            switch (choice) {

                case 1:
                    registerUser();
                    break;

                case 2:
                    loginUser();
                    break;

                case 3:
                    running = false;
                    System.out.println(
                            "Thank you for using SpacePark!");
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Try again.");
            }
        }

        sc.close();
    }

    // USER REGISTRATION
    private static void registerUser() {

        System.out.println("\n------ USER REGISTRATION ------");

        auth.registerUser(sc);
    }

    // USER LOGIN
    private static void loginUser() {

        System.out.println("\n---------- LOGIN ----------");

        try {

            User user = auth.loginUser(sc);

            if (user == null) {

                System.out.println(
                        "Login failed. Please try again.");

                return;
            }

            if (user instanceof Customer) {

                customerMenu((Customer) user);
            }
            else if (user instanceof Admin) {

                adminMenu((Admin) user);
            }
            else if (user instanceof Operator) {

                operatorMenu((Operator) user);
            }
            else {

                System.out.println("Unknown user role.");
            }

        }
        catch (InvalidUserException e) {

            System.out.println(e.getMessage());
        }
    }

    // CUSTOMER MENU
    private static void customerMenu(Customer customer) {

        boolean active = true;

        while (active) {

            System.out.println(
                    "\n========== CUSTOMER MENU ==========");

            System.out.println("1. Add Vehicle");
            System.out.println("2. View My Vehicles");
            System.out.println("3. Check Available Slots");
            System.out.println("4. Book Parking Slot");
            System.out.println("5. Manage EV Charging");
            System.out.println("6. View Parking History");
            System.out.println("7. Logout");

            System.out.print("Enter choice: ");

            switch (readChoice()) {

                case 1:
                    vehicleService.addVehicle(customer, sc);
                    break;

                case 2:
                    vehicleService.viewVehicles(customer);
                    break;

                case 3:
                    parkingService.checkAvailability();
                    break;

                case 4:
                    bookingService.createBooking(customer, sc);
                    break;

                case 5:
                    chargingService.manageCharging(customer, sc);
                    break;

                case 6:
                    reportService.viewCustomerHistory(customer);
                    break;

                case 7:
                    active = false;
                    System.out.println(
                            "Customer logged out.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // OPERATOR MENU
    private static void operatorMenu(Operator operator) {

        boolean active = true;

        while (active) {

            System.out.println(
                    "\n========== OPERATOR MENU ==========");

            System.out.println("1. View All Parking Slots");
            System.out.println("2. Record Vehicle Entry");
            System.out.println("3. Record Vehicle Exit");
            System.out.println("4. Calculate Parking Fee");
            System.out.println("5. View Active Parking");
            System.out.println("6. Logout");

            System.out.print("Enter choice: ");

            switch (readChoice()) {

                case 1:
                    parkingService.viewAllSlots();
                    break;

                case 2:
                    entryService.recordEntry(sc);
                    break;

                case 3:
                    exitService.recordExit(sc);
                    break;

                case 4:
                    billingService.calculateFee(sc);
                    break;

                case 5:
                    parkingService.viewActiveParking();
                    break;

                case 6:
                    active = false;
                    System.out.println(
                            "Operator logged out.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ADMIN MENU
    private static void adminMenu(Admin admin) {

        boolean active = true;

        while (active) {

            System.out.println(
                    "\n========== ADMIN MENU ==========");

            System.out.println("1. Add Parking Slot");
            System.out.println("2. View All Slots");
            System.out.println("3. Update Slot Status");
            System.out.println("4. Manage Parking Rates");
            System.out.println("5. Manage Maintenance");
            System.out.println("6. View Registered Users");
            System.out.println("7. View Parking Records");
            System.out.println("8. View Revenue Report");
            System.out.println("9. Logout");

            System.out.print("Enter choice: ");

            switch (readChoice()) {

                case 1:
                    parkingService.addSlot(sc);
                    break;

                case 2:
                    parkingService.viewAllSlots();
                    break;

                case 3:
                    parkingService.updateSlotStatus(sc);
                    break;

                case 4:
                    billingService.updateRates(sc);
                    break;

                case 5:
                    parkingService.manageMaintenance(sc);
                    break;

                case 6:

                    // Display all registered users
                    for (User user : auth.getAllUsers()) {

                        user.displayUserDetails();
                    }

                    break;

                case 7:
                    reportService.generateParkingReport();
                    break;

                case 8:
                    reportService.generateRevenueReport();
                    break;

                case 9:
                    active = false;
                    System.out.println(
                            "Admin logged out.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // SAFE INTEGER INPUT
    private static int readChoice() {

        while (!sc.hasNextInt()) {

            System.out.print(
                    "Please enter a valid number: ");

            sc.nextLine();
        }

        int choice = sc.nextInt();

        sc.nextLine();

        return choice;
    }
}
