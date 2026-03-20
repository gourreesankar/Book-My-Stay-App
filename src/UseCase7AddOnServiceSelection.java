/**
 * Hotel Booking Management System
 * Use Case 7: Add-On Service Selection
 *
 * Version: 7.0
 *
 * Demonstrates attaching optional services to reservations
 * using Map and List (composition).
 */

import java.util.*;

// Service class (Add-On)
class AddOnServiceUC7 {
    private String serviceName;
    private double cost;

    public AddOnServiceUC7(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getServiceName() {
        return serviceName;
    }
}

// Manager class
class AddOnServiceManagerUC7 {

    // reservationId → list of services
    private Map<String, List<AddOnServiceUC7>> serviceMap;

    public AddOnServiceManagerUC7() {
        serviceMap = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, AddOnServiceUC7 service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.getServiceName() +
                " to Reservation: " + reservationId);
    }

    // Display services
    public void displayServices(String reservationId) {

        System.out.println("\nServices for Reservation: " + reservationId);

        List<AddOnServiceUC7> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnServiceUC7 s : services) {
            System.out.println("- " + s.getServiceName() + " (₹" + s.getCost() + ")");
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<AddOnServiceUC7> services = serviceMap.get(reservationId);

        if (services != null) {
            for (AddOnServiceUC7 s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// Main class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v7.0 =====");

        AddOnServiceManagerUC7 manager = new AddOnServiceManagerUC7();

        // Example reservation IDs
        String res1 = "RES101";
        String res2 = "RES102";

        // Add services
        manager.addService(res1, new AddOnServiceUC7("Breakfast", 300));
        manager.addService(res1, new AddOnServiceUC7("Airport Pickup", 800));

        manager.addService(res2, new AddOnServiceUC7("Extra Bed", 500));

        // Display services
        manager.displayServices(res1);
        manager.displayServices(res2);

        // Calculate cost
        System.out.println("\nTotal Add-On Cost for " + res1 + ": ₹" +
                manager.calculateTotalCost(res1));

        System.out.println("Total Add-On Cost for " + res2 + ": ₹" +
                manager.calculateTotalCost(res2));

        System.out.println("\nCore booking and inventory remain unchanged.");
    }
}
