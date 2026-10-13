package org.example;

public enum SeatClass {

    ECONOMY(1.0),
    BUSINESS(2.2),
    FIRST(3.5);

    private final double multiplier;

    SeatClass(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
