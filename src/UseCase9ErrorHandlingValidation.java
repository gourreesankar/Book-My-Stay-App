/**
 * Hotel Booking Management System
 * Use Case 9: Error Handling & Validation
 *
 * Version: 9.0
 *
 * Demonstrates validation and custom exception handling.
 */

import java.util.HashMap;

// Custom Exception
class InvalidBookingExceptionUC9 extends Exception {
    public InvalidBookingExceptionUC9(String message) {
        super(message);
    }
}

// Validator class
class BookingValidatorUC9 {

    private HashMap<String, Integer> inventory;

    public BookingValidatorUC9() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    // Validate booking
    public void validate(String roomType) throws InvalidBookingExceptionUC9 {

        // Check valid room type
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingExceptionUC9("Invalid room type: " + roomType);
        }

        // Check availability
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingExceptionUC9("No availability for: " + roomType);
        }
    }

    // Safe booking (only after validation)
    public void bookRoom(String roomType) throws InvalidBookingExceptionUC9 {

        validate(roomType); // fail-fast

        // Reduce inventory safely
        inventory.put(roomType, inventory.get(roomType) - 1);

        System.out.println("Booking successful for: " + roomType);
    }
}

// Main class
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v9.0 =====");

        BookingValidatorUC9 validator = new BookingValidatorUC9();

        // Test cases
        String[] testInputs = {
                "Single Room",
                "Suite Room",     // invalid type
                "Double Room",
                "Double Room"     // no availability
        };

        for (String roomType : testInputs) {

            try {
                System.out.println("\nRequesting: " + roomType);
                validator.bookRoom(roomType);

            } catch (InvalidBookingExceptionUC9 e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("\nSystem continues running safely.");
    }
}