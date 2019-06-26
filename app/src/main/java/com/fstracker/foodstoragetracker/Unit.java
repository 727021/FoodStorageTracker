package com.fstracker.foodstoragetracker;

/**
 * An enumeration of units that FoodItems can be measured by.
 */
public enum Unit {
    COUNT("Count", "ct"),
    CUP("Cup", "c"),
    FLUID_OUNCE("Fluid Ounce", "fl oz"),
    GALLON("Gallon", "gal"),
    GRAM("Gram", "g"),
    KILOGRAM("Kilogram", "Kg"),
    LITER("Liter", "L"),
    MILLIGRAM("Milligram", "mg"),
    OUNCE("Ounce", "oz"),
    PINT("Pint", "pt"),
    POUND("Pound", "lbs"),
    QUART("Quart", "qt");

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
