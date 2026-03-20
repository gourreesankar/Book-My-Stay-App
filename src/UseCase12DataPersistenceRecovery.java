/**
 * Hotel Booking Management System
 * Use Case 12: Data Persistence & System Recovery
 *
 * Version: 12.0
 *
 * Demonstrates saving and restoring system state using serialization.
 */

import java.io.*;
import java.util.*;

// Serializable Booking class
class BookingUC12 implements Serializable {
    String reservationId;
    String roomType;

    public BookingUC12(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }
}

// Serializable System State
class SystemStateUC12 implements Serializable {
    Map<String, Integer> inventory;
    List<BookingUC12> bookings;

    public SystemStateUC12(Map<String, Integer> inventory,
                           List<BookingUC12> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceServiceUC12 {

    private static final String FILE_NAME = "system_state.dat";

    // Save state
    public void save(SystemStateUC12 state) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public SystemStateUC12 load() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemStateUC12) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading state: " + e.getMessage());
        }

        return null;
    }
}

// Main class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v12.0 =====");

        PersistenceServiceUC12 persistence = new PersistenceServiceUC12();

        // Try loading previous state
        SystemStateUC12 state = persistence.load();

        Map<String, Integer> inventory;
        List<BookingUC12> bookings;

        if (state == null) {
            // Fresh start
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);

            bookings = new ArrayList<>();

            System.out.println("Initialized new system state.");
        } else {
            // Restore
            inventory = state.inventory;
            bookings = state.bookings;

            System.out.println("Recovered inventory: " + inventory);
        }

        // Simulate new booking
        bookings.add(new BookingUC12("RES401", "Single Room"));
        inventory.put("Single Room", inventory.get("Single Room") - 1);

        System.out.println("New booking added and inventory updated.");

        // Save state before exit
        persistence.save(new SystemStateUC12(inventory, bookings));

        System.out.println("System ready for next restart.");
    }
}