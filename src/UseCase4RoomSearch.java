/**
 * Hotel Booking Management System
 * Use Case 4: Room Search & Availability Check
 *
 * Version: 4.0
 *
 * Demonstrates read-only access to inventory and filtering available rooms.
 */

import java.util.HashMap;

// Base class (unique name to avoid conflict)
abstract class RoomUC4 {
    protected String type;
    protected int beds;
    protected double price;

    public RoomUC4(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }

    public String getType() {
        return type;
    }
}

// Room types (renamed to avoid conflicts)
class SingleRoomUC4 extends RoomUC4 {
    public SingleRoomUC4() {
        super("Single Room", 1, 1500);
    }
}

class DoubleRoomUC4 extends RoomUC4 {
    public DoubleRoomUC4() {
        super("Double Room", 2, 2500);
    }
}

class SuiteRoomUC4 extends RoomUC4 {
    public SuiteRoomUC4() {
        super("Suite Room", 3, 5000);
    }
}

// Inventory (renamed to avoid UC3 conflict)
class RoomInventoryUC4 {
    private HashMap<String, Integer> inventory;

    public RoomInventoryUC4() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // unavailable
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// Search Service (read-only)
class RoomSearchService {

    public void searchAvailableRooms(RoomInventoryUC4 inventory, RoomUC4[] rooms) {

        System.out.println("\n--- Available Rooms ---");

        for (RoomUC4 room : rooms) {

            int available = inventory.getAvailability(room.getType());

            // Filter only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// Main class
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v4.0 =====");

        // Inventory (read-only)
        RoomInventoryUC4 inventory = new RoomInventoryUC4();

        // Room objects
        RoomUC4[] rooms = {
                new SingleRoomUC4(),
                new DoubleRoomUC4(),
                new SuiteRoomUC4()
        };

        // Search service
        RoomSearchService searchService = new RoomSearchService();

        // Perform search
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("Search Completed.");
    }
}