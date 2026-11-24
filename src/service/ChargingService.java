
package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import model.ChargingSession;
import model.ChargingStation;
import model.Customer;

public class ChargingService {

    private ArrayList<ChargingStation> stations;
    private ArrayList<ChargingSession> sessions;

    private int sessionCounter;

    private static final double CHARGING_RATE = 12.0;

    public ChargingService() {

        stations = new ArrayList<>();
        sessions = new ArrayList<>();

        sessionCounter = 1;

        stations.add(new ChargingStation(
                "CS101",
                "Fast Charging Station",
                "Floor 1",
                "Fast",
                50.0
        ));

        stations.add(new ChargingStation(
                "CS102",
                "Normal Charging Station",
                "Floor 1",
                "Normal",
                22.0
        ));

        stations.add(new ChargingStation(
                "CS103",
                "EV Charging Station",
                "Floor 2",
                "Fast",
                60.0
        ));
    }

    public void manageCharging(Customer customer, Scanner sc) {

        if (customer == null || sc == null) {
            System.out.println("Invalid customer or input.");
            return;
        }

        boolean active = true;

        while (active) {

            System.out.println("\n========== EV CHARGING MENU ==========");
            System.out.println("1. View Charging Stations");
            System.out.println("2. Start Charging");
            System.out.println("3. Stop Charging");
            System.out.println("4. View Charging History");
            System.out.println("5. View Active Sessions");
            System.out.println("6. Back to Customer Menu");

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
                    viewChargingStations();
                    break;

                case 2:
                    startCharging(sc);
                    break;

                case 3:
                    stopCharging(sc);
                    break;

                case 4:
                    viewChargingHistory();
                    break;

                case 5:
                    viewActiveSessions();
                    break;

                case 6:
                    active = false;
                    System.out.println("Returning to Customer Menu.");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public void viewChargingStations() {

        System.out.println("\n========== CHARGING STATIONS ==========");

        for (ChargingStation station : stations) {
            station.displayStationDetails();
        }
    }

    public void startCharging(Scanner sc) {

        System.out.println("\n========== START CHARGING ==========");

        viewChargingStations();

        System.out.print("\nEnter Station ID: ");
        String stationId = sc.nextLine().trim();

        ChargingStation selectedStation = null;

        for (ChargingStation station : stations) {

            if (station.getStationId().equalsIgnoreCase(stationId)) {
                selectedStation = station;
                break;
            }
        }

        if (selectedStation == null) {
            System.out.println("Charging station not found!");
            return;
        }

        if (!selectedStation.isAvailable()) {
            System.out.println(
                    "Charging station is currently occupied!"
            );
            return;
        }

        System.out.print("Enter EV Registration Number: ");
        String vehicleNumber = sc.nextLine().trim();

        if (vehicleNumber.isEmpty()) {
            System.out.println("Vehicle number cannot be empty!");
            return;
        }

        for (ChargingSession session : sessions) {

            if (session.getVehicleNumber().equalsIgnoreCase(vehicleNumber)
                    && session.getStatus().equalsIgnoreCase("ACTIVE")) {

                System.out.println(
                        "This vehicle already has an active charging session!"
                );
                return;
            }
        }

        String sessionId = "CHG" + sessionCounter;

        try {

            ChargingSession session = new ChargingSession(
                    sessionId,
                    stationId,
                    vehicleNumber,
                    LocalDateTime.now()
            );

            sessions.add(session);
            selectedStation.setAvailable(false);

            sessionCounter++;

            System.out.println("\nCharging Started Successfully!");
            System.out.println("Session ID: " + sessionId);
            System.out.println("Station ID: " + stationId);
            System.out.println("Vehicle Number: " + vehicleNumber);
            System.out.println(
                    "Charging Rate: Rs. " + CHARGING_RATE + " per kWh"
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                    "Unable to start charging: " + e.getMessage()
            );
        }
    }

    public void stopCharging(Scanner sc) {

        System.out.println("\n========== STOP CHARGING ==========");

        System.out.print("Enter Session ID: ");
        String sessionId = sc.nextLine().trim();

        ChargingSession selectedSession = null;

        for (ChargingSession session : sessions) {

            if (session.getSessionId().equalsIgnoreCase(sessionId)
                    && session.getStatus().equalsIgnoreCase("ACTIVE")) {

                selectedSession = session;
                break;
            }
        }

        if (selectedSession == null) {
            System.out.println("Active charging session not found!");
            return;
        }

        System.out.print("Enter Energy Consumed (kWh): ");

        double energy;

        try {
            energy = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid energy value!");
            return;
        }

        if (energy <= 0
                || Double.isNaN(energy)
                || Double.isInfinite(energy)) {

            System.out.println(
                    "Energy must be a valid value greater than zero!"
            );
            return;
        }

        double cost = energy * CHARGING_RATE;

        try {

            selectedSession.setEnergyConsumed(energy);
            selectedSession.setChargingCost(cost);
            selectedSession.setEndTime(LocalDateTime.now());
            selectedSession.setStatus("COMPLETED");

            for (ChargingStation station : stations) {

                if (station.getStationId().equalsIgnoreCase(
                        selectedSession.getStationId())) {

                    station.setAvailable(true);
                    break;
                }
            }

            System.out.println("\nCharging Stopped Successfully!");
            System.out.println("Energy Consumed: " + energy + " kWh");
            System.out.println("Charging Rate: Rs. " + CHARGING_RATE);
            System.out.println("Total Charging Cost: Rs. " + cost);

        } catch (IllegalArgumentException e) {
            System.out.println(
                    "Unable to stop charging: " + e.getMessage()
            );
        }
    }

    public void viewChargingHistory() {

        System.out.println("\n========== CHARGING HISTORY ==========");

        if (sessions.isEmpty()) {
            System.out.println("No charging history available.");
            return;
        }

        for (ChargingSession session : sessions) {
            session.displaySessionDetails();
        }
    }

    public void viewActiveSessions() {

        System.out.println(
                "\n========== ACTIVE CHARGING SESSIONS =========="
        );

        boolean found = false;

        for (ChargingSession session : sessions) {

            if (session.getStatus().equalsIgnoreCase("ACTIVE")) {
                session.displaySessionDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No active charging sessions.");
        }
    }

    public ArrayList<ChargingSession> getAllSessions() {
        return new ArrayList<>(sessions);
    }

    public ArrayList<ChargingStation> getAllStations() {
        return new ArrayList<>(stations);
    }
}
