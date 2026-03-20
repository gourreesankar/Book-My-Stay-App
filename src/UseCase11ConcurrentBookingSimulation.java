/**
 * Hotel Booking Management System
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 *
 * Version: 11.0
 *
 * Demonstrates thread-safe booking using synchronized blocks.
 */

import java.util.*;

// Reservation class
class ReservationUC11 {
    String guestName;
    String roomType;

    public ReservationUC11(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Inventory (critical resource)
class InventoryServiceUC11 {

    private Map<String, Integer> inventory;

    public InventoryServiceUC11() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
    }

    // synchronized method (critical section)
    public synchronized boolean allocateRoom(String roomType, String guestName) {

        if (inventory.get(roomType) > 0) {

            // simulate processing delay
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            inventory.put(roomType, inventory.get(roomType) - 1);

            System.out.println("Booking Confirmed for " + guestName +
                    " | Remaining: " + inventory.get(roomType));

            return true;

        } else {
            System.out.println("Booking Failed for " + guestName + " (No rooms left)");
            return false;
        }
    }
}

// Booking Processor (Thread)
class BookingProcessorUC11 extends Thread {

    private Queue<ReservationUC11> queue;
    private InventoryServiceUC11 inventory;

    public BookingProcessorUC11(Queue<ReservationUC11> queue,
                                InventoryServiceUC11 inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            ReservationUC11 req;

            // synchronized queue access
            synchronized (queue) {
                if (queue.isEmpty()) break;
                req = queue.poll();
            }

            // critical section handled inside inventory
            inventory.allocateRoom(req.roomType, req.guestName);
        }
    }
}

// Main class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v11.0 =====");

        // Shared queue
        Queue<ReservationUC11> queue = new LinkedList<>();

        // Add requests
        queue.offer(new ReservationUC11("Alice", "Single Room"));
        queue.offer(new ReservationUC11("Bob", "Single Room"));
        queue.offer(new ReservationUC11("Charlie", "Single Room"));

        InventoryServiceUC11 inventory = new InventoryServiceUC11();

        // Create multiple threads
        Thread t1 = new BookingProcessorUC11(queue, inventory);
        Thread t2 = new BookingProcessorUC11(queue, inventory);

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {}

        System.out.println("\nAll bookings processed safely.");
    }
}