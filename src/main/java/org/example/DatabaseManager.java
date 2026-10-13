package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    public static final String DB_URL = "jdbc:h2:./flight_db";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);

    }

    public static void initDatabase() {
        String sqlFlights = "CREATE TABLE IF NOT EXISTS flights (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "flight_number VARCHAR(20) NOT NULL, " +
                "origin VARCHAR(100) NOT NULL, " +
                "destination VARCHAR(100) NOT NULL, " +
                "base_price DOuBLE NOT NULL, " +
                "total_capacity INT NOT NULL, " +
                "available_seats INT NOT NULL" +
                ");";

        String sqlBookings = "CREATE TABLE IF NOT EXISTS bookings (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "flight_id INT NOT NULL, " +
                "passenger_name VARCHAR(1000) NOT NULL, " +
                "passport_number VARCHAR(50) NOT NULL, " +
                "seat_class VARCHAR(20) NOT NULL, " +
                "paid_fare DOuBLE NOT NULL, " +
                "status VARCHAR(20) NOT NULL, " +
                "FOREIGN KEY (flight_id) REFERENCES flights(id) " +
                ");";

        try (Connection conn = getConnection();
        Statement stmt = conn.createStatement()) {

            stmt.execute(sqlFlights);
            stmt.execute(sqlBookings);
            System.out.println("💾 Database tables ('flights' & 'bookings') initialized successfully!");

        } catch (SQLException e) {
            System.out.println("❌ Database initialization error: " + e.getMessage());
        }
    }
}
