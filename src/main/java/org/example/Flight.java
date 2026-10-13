package org.example;

public class Flight {

    private int id;
    private String flightNumber;
    private String origin;
    private String destination;
    private double basePrice;
    private int totalCapacity;
    private int availableSeats;

    public Flight(String flightNumber, String origin, String destination, double basePrice,
                  int totalCapacity, int availableSeats) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.basePrice = basePrice;
        this.totalCapacity = totalCapacity;
        this.availableSeats = availableSeats;
    }

    public Flight(int id, String flightNumber, String origin, String destination, double basePrice,
                  int totalCapacity, int availableSeats) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.basePrice = basePrice;
        this.totalCapacity = totalCapacity;
        this.availableSeats = availableSeats;
    }

    public int getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return String.format("[#%d] %-8s | %-16s -> %-16s | Base: $%-6.2f | Seats: %d/%d",
                id, flightNumber, origin, destination, basePrice, availableSeats, totalCapacity);
    }
}
