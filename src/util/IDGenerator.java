
package util;

public class IDGenerator {

    private static int userCounter = 1001;
    private static int vehicleCounter = 2001;
    private static int bookingCounter = 3001;
    private static int ticketCounter = 4001;
    private static int sessionCounter = 5001;
    private static int maintenanceCounter = 6001;

    public static synchronized String generateUserID() {

        return "U" + userCounter++;
    }

    public static synchronized String generateVehicleID() {

        return "V" + vehicleCounter++;
    }

    public static synchronized String generateBookingID() {

        return "B" + bookingCounter++;
    }

    public static synchronized String generateTicketID() {

        return "T" + ticketCounter++;
    }

    public static synchronized String generateSessionID() {

        return "CS" + sessionCounter++;
    }

    public static synchronized String generateMaintenanceID() {

        return "MR" + maintenanceCounter++;
    }
}
