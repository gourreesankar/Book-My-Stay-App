/**
 * Hotel Booking Management System
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Version: 10.0
 *
 * Demonstrates safe cancellation using Stack (LIFO rollback).
 */

import java.util.*;

// Reservation class
class ReservationUC10 {
    String reservationId;
    String roomType;

    public ReservationUC10(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }
}

// Inventory Service
class InventoryServiceUC10 {
    private Map<String, Integer> inventory;

    public InventoryServiceUC10() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
    }

    public void increase(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void display() {
        System.out.println("\nCurrent Inventory: " + inventory);
    }
}

// Cancellation Service
class CancellationServiceUC10 {

    private Map<String, ReservationUC10> confirmedBookings;
    private Stack<String> rollbackStack; // stores released room IDs

    public CancellationServiceUC10() {
        confirmedBookings = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    // Add confirmed booking (simulate existing bookings)
    public void addBooking(String roomId, ReservationUC10 reservation) {
        confirmedBookings.put(roomId, reservation);
    }

    // Cancel booking
    public void cancelBooking(String roomId, InventoryServiceUC10 inventory) {

        System.out.println("\nAttempting cancellation for Room ID: " + roomId);

        // Validate existence
        if (!confirmedBookings.containsKey(roomId)) {
            System.out.println("Error: Booking does not exist or already cancelled.");
            return;
        }

        ReservationUC10 res = confirmedBookings.get(roomId);

        // Push to rollback stack (LIFO)
        rollbackStack.push(roomId);

        // Restore inventory
        inventory.increase(res.roomType);

        // Remove booking
        confirmedBookings.remove(roomId);

        System.out.println("Cancellation successful for Room ID: " + roomId);
    }

    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (LIFO): " + rollbackStack);
    }
}

// Main class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v10.0 =====");

        InventoryServiceUC10 inventory = new InventoryServiceUC10();
        CancellationServiceUC10 service = new CancellationServiceUC10();

        // Simulate confirmed bookings
        service.addBooking("R101", new ReservationUC10("RES301", "Single Room"));
        service.addBooking("R102", new ReservationUC10("RES302", "Double Room"));

        // Cancel bookings
        service.cancelBooking("R101", inventory);
        service.cancelBooking("R999", inventory); // invalid
        service.cancelBooking("R102", inventory);

        // Display results
        inventory.display();
        service.displayRollbackStack();

        System.out.println("\nRollback completed safely.");
    }
}