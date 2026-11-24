
package service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import model.Booking;
import model.Customer;
import model.ParkingSlot;

public class BookingService {

    private static final Map<String, Booking> SHARED_BOOKINGS =
            new LinkedHashMap<>();

    private final SlotAllocationService allocationService;
    private final QueueManager queueManager;
    private final Map<String, Booking> bookings;

    // Default constructor used by Main.java and ParkingService
    public BookingService() {
        this(
            ParkingService.getSharedAllocationService(),
            ParkingService.getSharedQueueManager(),
            SHARED_BOOKINGS
        );
    }

    // Constructor for independent testing
    public BookingService(
            SlotAllocationService allocationService,
            QueueManager queueManager) {

        this(allocationService, queueManager, new LinkedHashMap<>());
    }

    // Internal constructor
    private BookingService(
            SlotAllocationService allocationService,
            QueueManager queueManager,
            Map<String, Booking> bookings) {

        if (allocationService == null || queueManager == null) {
            throw new IllegalArgumentException(
                    "Services cannot be null.");
        }

        this.allocationService = allocationService;
        this.queueManager = queueManager;
        this.bookings = bookings;
    }

    // CREATE BOOKING THROUGH CUSTOMER MENU
    public Booking createBooking(Customer customer, Scanner sc) {

        if (customer == null) {
            System.out.println(
                    "Please login before booking a slot.");
            return null;
        }

        System.out.println("===== PARKING BOOKING =====");

        System.out.print("Enter Booking ID: ");
        String bookingId = sc.nextLine().trim();

        System.out.print("Enter Vehicle Number: ");
        String vehicleNumber = sc.nextLine().trim();

        System.out.print("Enter Vehicle Type (Car/Bike/EV): ");
        String vehicleType = sc.nextLine().trim();

        try {
            return createBooking(
                    bookingId, vehicleNumber, vehicleType);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // CREATE BOOKING
    public synchronized Booking createBooking(
            String bookingId,
            String vehicleNumber,
            String vehicleType) {

        if (bookingId == null || bookingId.trim().isEmpty()
                || vehicleNumber == null
                || vehicleNumber.trim().isEmpty()
                || vehicleType == null
                || vehicleType.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Booking details cannot be empty.");
        }

        bookingId = bookingId.trim();
        vehicleNumber = vehicleNumber.trim();
        vehicleType = vehicleType.trim();

        String normalizedBookingId =
                bookingId.toUpperCase();

        if (bookings.containsKey(normalizedBookingId)) {
            throw new IllegalArgumentException(
                    "Booking ID already exists: " + bookingId);
        }

        Booking booking = new Booking(
                bookingId, vehicleNumber, vehicleType);

        ParkingSlot slot =
                allocationService.allocateSlot(vehicleType);

        if (slot != null) {

            booking.setSlotId(slot.getSlotId());
            booking.setStatus("ALLOCATED");

            System.out.println(
                    "Slot allocated successfully: "
                    + slot.getSlotId());

        } else {

            queueManager.addToQueue(booking);

            System.out.println(
                    "No matching slot available. "
                    + "Booking added to waiting queue.");
        }

        bookings.put(normalizedBookingId, booking);

        return booking;
    }

    // GET BOOKING
    public synchronized Booking getBooking(String bookingId) {

        if (bookingId == null) {
            return null;
        }

        return bookings.get(
                bookingId.trim().toUpperCase());
    }

    // PROCESS WAITING QUEUE
    public synchronized void processWaitingQueue() {

        while (queueManager.getQueueSize() > 0) {

            Booking allocated =
                    queueManager.processNextRequest(
                            allocationService);

            if (allocated == null) {
                break;
            }

            System.out.println(
                    "Waiting booking allocated: " + allocated);
        }
    }

    // DISPLAY ALL BOOKINGS
    public synchronized void displayBookings() {

        System.out.println("===== BOOKINGS =====");

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking booking : bookings.values()) {
            System.out.println(booking);
        }
    }
}
