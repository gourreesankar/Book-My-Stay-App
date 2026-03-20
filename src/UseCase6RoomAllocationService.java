/**
 * Hotel Booking Management System
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Version: 6.0
 *
 * Demonstrates safe room allocation using Queue, Set, and HashMap.
 */

import java.util.*;

// Reservation (same idea as UC5 but renamed)
class ReservationUC6 {
    String guestName;
    String roomType;

    public ReservationUC6(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory Service
class InventoryServiceUC6 {
    private HashMap<String, Integer> inventory;

    public InventoryServiceUC6() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void reduceAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

// Booking Service
class BookingServiceUC6 {

    private Set<String> allocatedRoomIds; // prevents duplicates
    private HashMap<String, Set<String>> allocationMap; // roomType → IDs

    public BookingServiceUC6() {
        allocatedRoomIds = new HashSet<>();
        allocationMap = new HashMap<>();
    }

    public void processBookings(Queue<ReservationUC6> queue, InventoryServiceUC6 inventory) {

        System.out.println("\n--- Processing Bookings ---");

        while (!queue.isEmpty()) {

            ReservationUC6 req = queue.poll();

            String type = req.roomType;

            if (inventory.getAvailability(type) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(type);

                // Ensure uniqueness
                while (allocatedRoomIds.contains(roomId)) {
                    roomId = generateRoomId(type);
                }

                allocatedRoomIds.add(roomId);

                // Map room type to allocated IDs
                allocationMap.putIfAbsent(type, new HashSet<>());
                allocationMap.get(type).add(roomId);

                // Update inventory immediately
                inventory.reduceAvailability(type);

                System.out.println("Confirmed: " + req.guestName +
                        " → " + type + " | Room ID: " + roomId);

            } else {
                System.out.println("Rejected (No availability): " + req.guestName +
                        " → " + type);
            }
        }
    }

    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + new Random().nextInt(1000);
    }
}

// Main class
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v6.0 =====");

        // Queue (FIFO)
        Queue<ReservationUC6> queue = new LinkedList<>();

        queue.offer(new ReservationUC6("Alice", "Single Room"));
        queue.offer(new ReservationUC6("Bob", "Single Room"));
        queue.offer(new ReservationUC6("Charlie", "Single Room")); // exceeds
        queue.offer(new ReservationUC6("David", "Suite Room"));

        // Services
        InventoryServiceUC6 inventory = new InventoryServiceUC6();
        BookingServiceUC6 bookingService = new BookingServiceUC6();

        // Process bookings
        bookingService.processBookings(queue, inventory);

        System.out.println("\nAllocation Completed.");
    }
}