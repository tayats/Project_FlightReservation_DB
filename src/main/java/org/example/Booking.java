package org.example;

public class Booking {

    private int id;
    private int flightId;
    private String passengerName;
    private String passportNumber;
    private SeatClass seatClass;
    private double paidFare;
    private String status;

    public Booking(int flightId, String passengerName, String passportNumber, SeatClass seatClass,
                   double paidFare) {
        this.flightId = flightId;
        this.passengerName = passengerName;
        this.passportNumber = passportNumber;
        this.seatClass = seatClass;
        this.paidFare = paidFare;
        this.status = "CONFIRMED";
    }

    public Booking(int id, int flightId, String passengerName, String passportNumber, SeatClass seatClass,
                   double paidFare, String status) {
        this.id = id;
        this.flightId = flightId;
        this.passengerName = passengerName;
        this.passportNumber = passportNumber;
        this.seatClass = seatClass;
        this.paidFare = paidFare;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getFlightId() {
        return flightId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public double getPaidFare() {
        return paidFare;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("[Booking #%d] Passenger: %-15s (%-9s) | Flight ID: #%-2d | Class: %-8s | Paid: $%-6.2f | Status: %s",
                id, passengerName, passportNumber, flightId, seatClass, paidFare, status);
    }
}
