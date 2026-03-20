/**
 * Hotel Booking Management System
 * Use Case 8: Booking History & Reporting
 *
 * Version: 8.0
 *
 * Demonstrates storing booking history and generating reports.
 */

import java.util.*;

// Reservation (history object)
class ReservationUC8 {
    private String reservationId;
    private String guestName;
    private String roomType;

    public ReservationUC8(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// Booking History (List → ordered storage)
class BookingHistoryUC8 {

    private List<ReservationUC8> history;

    public BookingHistoryUC8() {
        history = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(ReservationUC8 reservation) {
        history.add(reservation);
        System.out.println("Added to history: " + reservation.getReservationId());
    }

    // Get all history
    public List<ReservationUC8> getAllReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportServiceUC8 {

    // Display all bookings
    public void generateFullReport(List<ReservationUC8> history) {

        System.out.println("\n--- Booking History Report ---");

        for (ReservationUC8 r : history) {
            r.display();
        }
    }

    // Summary report
    public void generateSummary(List<ReservationUC8> history) {

        System.out.println("\n--- Summary Report ---");

        System.out.println("Total Bookings: " + history.size());

        Map<String, Integer> countMap = new HashMap<>();

        for (ReservationUC8 r : history) {
            countMap.put(r.getRoomType(),
                    countMap.getOrDefault(r.getRoomType(), 0) + 1);
        }

        for (String type : countMap.keySet()) {
            System.out.println(type + " → " + countMap.get(type));
        }
    }
}

// Main class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v8.0 =====");

        BookingHistoryUC8 history = new BookingHistoryUC8();

        // Add confirmed bookings
        history.addReservation(new ReservationUC8("RES201", "Alice", "Single Room"));
        history.addReservation(new ReservationUC8("RES202", "Bob", "Double Room"));
        history.addReservation(new ReservationUC8("RES203", "Charlie", "Single Room"));

        // Reporting
        BookingReportServiceUC8 reportService = new BookingReportServiceUC8();

        reportService.generateFullReport(history.getAllReservations());
        reportService.generateSummary(history.getAllReservations());

        System.out.println("\nReporting completed.");
    }
}