# ✈️ SkyWings Enterprise - Relational Airline & Seat Reservation Platform

An enterprise commercial flight management and seat inventory reservation platform built with Core Java and a relational SQL database. SkyWings transitions from console collections to an enterprise relational persistence layer using **JDBC**, **H2 Database**, and the **DAO (Data Access Object) Pattern**.

---

## 🌟 Key Features

- **Relational Booking Schema**: Multi-table architecture connecting `bookings` to `flights` via **Foreign Keys (`FOREIGN KEY (flight_id) REFERENCES flights(id)`)**.
- **Rich Enum Multipliers**: Dynamic cabin pricing (`SeatClass.ECONOMY` = 1.0x, `BUSINESS` = 2.2x, `FIRST` = 3.5x).
- **Atomic Overbooking Safeguards**: Database-level seat deduction queries enforcing capacity limits (`UPDATE flights SET available_seats = available_seats - 1 WHERE id = ? AND available_seats > 0`).
- **Automated Seat Restoration**: Booking cancellations immediately restore available seat counts back to flight manifests.
- **SQL Revenue Aggregations**: Real-time flight revenue analytics powered by SQL aggregate functions (`SELECT SUM(paid_fare) WHERE status = 'CONFIRMED'`).

---

## 🏛️ Architecture & Technologies Applied

| Component / Pattern | Implementation Details |
| :--- | :--- |
| **Relational Database** | Embedded H2 SQL engine with tables, constraints, and Foreign Keys. |
| **JDBC API** | Parameterized `PreparedStatement` eliminating SQL Injection risks. |
| **DAO Pattern** | Decoupled architecture with `FlightDao` and `BookingDao`. |
| **Rich Enums** | `SeatClass` with constructor multipliers. |
| **Maven** | Modern dependency management and build lifecycle (`pom.xml`). |

---

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK 11 or higher)
- Apache Maven

### Running the Application
1. Clone the repository:
   ```bash
   git clone https://github.com/tayats/Project_FlightReservation_DB.git