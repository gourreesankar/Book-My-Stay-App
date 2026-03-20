/**
 * Hotel Booking Management System
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Version: 5.0
 *
 * Demonstrates booking request handling using Queue (FIFO).
 */

import java.util.LinkedList;
import java.util.Queue;

// Reservation class (represents booking request)
class ReservationUC5 {
    private String guestName;
    private String roomType;

    public ReservationUC5(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType);
    }
}

// Booking Queue Manager
class BookingQueueUC5 {

    private Queue<ReservationUC5> queue;

    public BookingQueueUC5() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(ReservationUC5 reservation) {
        queue.offer(reservation);
        System.out.println("Request added:");
        reservation.displayReservation();
    }

    // Display all requests (FIFO order)
    public void displayQueue() {
        System.out.println("\n--- Booking Requests (FIFO Order) ---");

        for (ReservationUC5 r : queue) {
            r.displayReservation();
        }
    }
}

// Main class
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v5.0 =====");

        BookingQueueUC5 bookingQueue = new BookingQueueUC5();

        // Add booking requests
        bookingQueue.addRequest(new ReservationUC5("Alice", "Single Room"));
        bookingQueue.addRequest(new ReservationUC5("Bob", "Double Room"));
        bookingQueue.addRequest(new ReservationUC5("Charlie", "Suite Room"));

        // Display queue
        bookingQueue.displayQueue();

        System.out.println("\nAll requests stored in arrival order (FIFO).");
    }
}
