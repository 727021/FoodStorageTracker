package com.fstracker.foodstoragetracker;

public enum Unit {
    // Temporary units (these will change)
    UNIT_1("Unit 1", "u1"),
    UNIT_2("Unit 2", "u2"),
    UNIT_3("Unit 3", "u3"),
    UNIT_4("Unit 4", "u4"),
    UNIT_5("Unit 5", "u5");

    private String name;
    private String symbol;

    Unit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        // This can be changed once we know how we want units displayed
        return String.format("%s (%s)", name, symbol);
    }
}
