package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingDao {

    public Booking createBooking(Booking booking) {
        String sqlBookings = "INSERT INTO bookings (flight_id, passenger_name, passport_number, seat_class, " +
                "paid_fare, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBookings, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, booking.getFlightId());
            stmt.setString(2, booking.getPassengerName());
            stmt.setString(3, booking.getPassportNumber());
            stmt.setString(4, booking.getSeatClass().name());
            stmt.setDouble(5, booking.getPaidFare());
            stmt.setString(6, booking.getStatus());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    booking.setId(generatedKeys.getInt(1));

                }
            }
            return booking;

        } catch (SQLException e) {
            System.out.println("❌ Database insert error: " + e.getMessage());
            return null;
        }
    }

    public Optional<Booking> findById(int id) {
        String sqlBookings = "SELECT * FROM bookings WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBookings)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToBooking(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Database query error: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        String sqlBookings = "SELECT * FROM bookings ORDER BY id ASC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBookings);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bookings.add(mapRowToBooking(rs));

            }
        } catch (SQLException e) {
            System.out.println("❌ Database query error: " + e.getMessage());
        }
        return bookings;
    }

    public boolean cancelBooking(int id) {
        String sqlBookings = "UPDATE bookings SET status = 'CANCELLED' WHERE id = ? AND status = 'CONFIRMED'";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBookings)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Update error: " + e.getMessage());
            return false;
        }
    }

    public double calculateFlightRevenue(int flightId) {
        String sqlBookings = "SELECT SUM(paid_fare) AS total_revenue FROM bookings WHERE flight_id = ? AND " +
                "status = 'CONFIRMED'";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBookings)) {

            stmt.setInt(1, flightId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total_revenue");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Update error: " + e.getMessage());
        }
        return 0.0;
    }

    private Booking mapRowToBooking(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("id"),
                rs.getInt("flight_id"),
                rs.getString("passenger_name"),
                rs.getString("passport_number"),
                SeatClass.valueOf(rs.getString("seat_class")),
                rs.getDouble("paid_fare"),
                rs.getString("status")
        );
    }
}
