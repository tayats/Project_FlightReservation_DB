package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao {

    public Flight insertFlight(Flight flight) {
        String sqlFlights = "INSERT INTO flights (flight_number, origin, destination, base_price, total_capacity, available_seats) \n" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlFlights, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, flight.getFlightNumber());
            stmt.setString(2, flight.getOrigin());
            stmt.setString(3, flight.getDestination());
            stmt.setDouble(4, flight.getBasePrice());
            stmt.setInt(5, flight.getTotalCapacity());
            stmt.setInt(6, flight.getAvailableSeats());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    flight.setId(generatedKeys.getInt(1));
                }
            }
            return flight;

        } catch (SQLException e) {
            System.out.println("❌ Database insert error: " + e.getMessage());
            return null;
        }
    }

    public Optional<Flight> findById(int id) {
        String sqlFlights = "SELECT * FROM flights WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlFlights)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToFlight(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Database query error: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Flight> findAll() {
        List<Flight> flights = new ArrayList<>();
        String sqlFlights = "SELECT * FROM flights ORDER BY id ASC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlFlights);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                flights.add(mapRowToFlight(rs));
            }
        } catch (SQLException e) {
            System.out.println("❌ Database query error: " + e.getMessage());
        }
        return flights;
    }

    public boolean deductSeat(int flightId) {
        String sqlFlights = "UPDATE flights SET available_seats = available_seats - 1 WHERE id = ? AND " +
                "available_seats > 0";

        try (Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sqlFlights)) {

            stmt.setInt(1, flightId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Update error: " + e.getMessage());
            return false;
        }
    }

    public boolean restoreSeat(int flightId) {
        String sqlFlights = "UPDATE flights SET available_seats = available_seats + 1 WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sqlFlights)) {

            stmt.setInt(1, flightId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Update error: " + e.getMessage());
            return false;
        }
    }

    private Flight mapRowToFlight(ResultSet rs) throws SQLException {
        return new Flight(
                rs.getInt("id"),
                rs.getString("flight_number"),
                rs.getString("origin"),
                rs.getString("destination"),
                rs.getDouble("base_price"),
                rs.getInt("total_capacity"),
                rs.getInt("available_seats")
        );
    }
}
