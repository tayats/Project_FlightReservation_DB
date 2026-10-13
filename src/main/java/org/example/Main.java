package org.example;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        DatabaseManager.initDatabase();
        FlightDao flightDao = new FlightDao();
        BookingDao bookingDao = new BookingDao();
        Scanner scanner = new Scanner(System.in);

        if (flightDao.findAll().isEmpty()) {
            flightDao.insertFlight(new Flight("TK-101", "Istanbul (IST)", "London (LHR)", 350.00, 150, 150));
            flightDao.insertFlight(new Flight("BA-202", "London (LHR)", "New York (JFK)", 650.00, 200, 200));
            flightDao.insertFlight(new Flight("LH-303", "Frankfurt (FRA)", "Tokyo (HND)", 800.00, 120, 120));
        }

        while (true) {
            System.out.println("\n==================================================");
            System.out.println("   THE FLIGHT OPERATIONS HUB   ");
            System.out.println("==================================================");
            System.out.println("1. View All Flights");
            System.out.println("2. Add New Flight Route");
            System.out.println("3. Book Ticket");
            System.out.println("4. View All Bookings)");
            System.out.println("5. Track Booking By Id)");
            System.out.println("6. Cancel Booking (Restores Seat)");
            System.out.println("7. Flight Revenue Dashboard");
            System.out.println("\n0. Exit");
            System.out.print("Please enter your choice (0-7): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    System.out.println("\n --- FLIGHT LIST ---");
                    flightDao.findAll().forEach(System.out::println);
                    break;

                case "2":
                    System.out.print("Enter flight number: ");
                    String flightNumber = scanner.nextLine().trim();

                    System.out.print("Enter origin: ");
                    String origin = scanner.nextLine().trim();

                    System.out.print("Enter destination: ");
                    String destination = scanner.nextLine().trim();

                    System.out.print("Enter base price: ");
                    String basePrice = scanner.nextLine().trim();

                    System.out.print("Enter total capacity: ");
                    String totalCapacity = scanner.nextLine().trim();

                    double priceVal = Double.parseDouble(basePrice);
                    int capVal = Integer.parseInt(totalCapacity);

                    Flight newFlight = new Flight(flightNumber, origin, destination, priceVal, capVal, capVal);
                    Flight saved = flightDao.insertFlight(newFlight);

                    if (saved != null) {
                        System.out.println("Flight route added with ID: #" + saved.getId());
                    }
                    break;

                case "3":
                    System.out.print("Enter passenger name: ");
                    String passengerName = scanner.nextLine().trim();

                    System.out.print("Enter passport number: ");
                    String passport = scanner.nextLine().trim();

                    System.out.print("Enter flight ID to book: ");
                    int flightId = Integer.parseInt(scanner.nextLine().trim());

                    Optional<Flight> optFlight = flightDao.findById(flightId);
                    if (optFlight.isEmpty()) {
                        System.out.println("Flight ID #" + flightId + " does not exist!");
                        break;
                    }
                    Flight flight = optFlight.get();

                    System.out.println("Select Seat Class: [1] ECONOMY (1.0x)  [2] BUSINESS (2.2x)  [3] FIRST (3.5x)");
                    String classChoice = scanner.nextLine().trim();

                    SeatClass seatClass;
                    if (classChoice.equals("2")) {
                        seatClass = SeatClass.BUSINESS;
                    } else if (classChoice.equals("3")) {
                        seatClass = SeatClass.FIRST;
                    } else {
                        seatClass = SeatClass.ECONOMY;
                    }

                    double fare = flight.getBasePrice() * seatClass.getMultiplier();

                    boolean seatDeducted = flightDao.deductSeat(flightId);
                    if (!seatDeducted) {
                        System.out.println("Booking rejected: Flight is completely FULL!");
                        break;
                    }

                    Booking newBooking = new Booking(flightId, passengerName, passport, seatClass, fare);
                    Booking savedBooking = bookingDao.createBooking(newBooking);

                    System.out.println("Ticket booked successfully!");
                    System.out.println("Booking Confirmation: " + savedBooking);
                    break;

                case "4":
                    System.out.println("\n --- ALL BOOKINGS ---");
                    bookingDao.findAll().forEach(System.out::println);
                    break;

                case "5":
                    System.out.print("Enter Booking ID to track: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());

                    Optional<Booking> optBooking = bookingDao.findById(id);
                    if (optBooking.isPresent()) {
                        System.out.println("\nOrder Details:");
                        System.out.println(optBooking.get());

                    } else {
                        System.out.println("No order found with ID: #" + id);
                    }
                    break;

                case "6":
                    System.out.print("Enter Booking ID to cancel: ");
                    int cancelId = Integer.parseInt(scanner.nextLine().trim());

                    Optional<Booking> toCancel = bookingDao.findById(cancelId);
                    if (toCancel.isEmpty()) {
                        System.out.println("Booking #" + cancelId + " does not exist!");
                        break;
                    }
                    Booking booking = toCancel.get();
                    boolean cancelled = bookingDao.cancelBooking(cancelId);

                    if (cancelled) {
                        flightDao.restoreSeat(booking.getFlightId());
                        System.out.println("✅ Booking #" + cancelId + " cancelled and seat restored to flight #"
                                + booking.getFlightId() + "!");
                    } else {
                        System.out.println("❌ Cancellation rejected: Booking is already CANCELLED!");
                    }
                    break;

                case "7":
                    System.out.print("Enter Flight ID for revenue report: ");
                    int revFlightId = Integer.parseInt(scanner.nextLine().trim());
                    double revenue = bookingDao.calculateFlightRevenue(revFlightId);
                    System.out.printf("Total Confirmed Revenue for Flight #%d: $%.2f\n", revFlightId, revenue);
                    break;

                case "0":
                    System.out.println("Exiting Flight Operations Hub. All records safely committed to SQL Database!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid selection. Please choose 0 to 7.");
            }
        }
    }
}
