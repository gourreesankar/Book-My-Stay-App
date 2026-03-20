/**
 * Hotel Booking Management System
 * Use Case 3: Centralized Room Inventory Management
 *
 * Version: 3.1
 *
 * Demonstrates centralized inventory using HashMap
 * to manage room availability efficiently.
 *
 * @author Goureesankar
 */

import java.util.HashMap;
import java.util.Map;

// Inventory Class
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor (initialize inventory)
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main Class
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v3.1 =====");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Example update (booking simulation)
        System.out.println("\nUpdating availability (booking 1 Single Room)...");

        int current = inventory.getAvailability("Single Room");
        inventory.updateAvailability("Single Room", current - 1);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication Finished.");
    }
}