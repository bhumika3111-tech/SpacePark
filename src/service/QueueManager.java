
package service;

import java.util.LinkedList;
import java.util.Queue;
import model.Booking;
import model.ParkingSlot;

public class QueueManager {

    private Queue<Booking> waitingQueue;

    public QueueManager() {
        waitingQueue = new LinkedList<>();
    }

    public void addToQueue(Booking booking) {

        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null.");
        }

        booking.setStatus("WAITING");
        waitingQueue.offer(booking);

        System.out.println("Added to waiting queue.");
        System.out.println("Queue Position: " + waitingQueue.size());
    }

    public Booking getNextRequest() {
        return waitingQueue.peek();
    }

    public Booking processNextRequest(
            SlotAllocationService allocationService) {

        if (allocationService == null) {
            throw new IllegalArgumentException(
                    "Allocation service cannot be null."
            );
        }

        Booking next = waitingQueue.peek();

        if (next == null) {
            return null;
        }

        ParkingSlot slot =
                allocationService.allocateSlot(next.getVehicleType());

        if (slot == null) {
            return null;
        }

        waitingQueue.poll();

        next.setSlotId(slot.getSlotId());
        next.setStatus("ALLOCATED");

        System.out.println(
                "Waiting booking " + next.getBookingId()
                        + " allocated to slot " + slot.getSlotId()
        );

        return next;
    }

    public int getQueueSize() {
        return waitingQueue.size();
    }

    public void displayQueue() {

        System.out.println("\n===== WAITING QUEUE =====");

        if (waitingQueue.isEmpty()) {
            System.out.println("No bookings are waiting.");
            return;
        }

        int position = 1;

        for (Booking booking : waitingQueue) {
            System.out.println(position + ". " + booking);
            position++;
        }
    }
}
