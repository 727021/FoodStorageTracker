package com.fstracker.foodstoragetracker;

/**
 * An enumeration of units that FoodItems can be measured by.
 */
public enum Unit {
    COUNT("Count", "ct"),
    FLUID_OUNCE("Fluid Ounce", "fl oz"),
    CUP("Cup", "c"),
    QUART("Quart", "qt"),
    PINT("Pint", "pt"),
    GALLON("Gallon", "gal"),
    MILLILITER("Milliliter", "mL"),
    LITER("Liter", "L"),
    GRAM("Gram", "g"),
    KILOGRAM("Kilogram", "Kg"),
    OUNCE("Ounce", "oz"),
    POUND("Pound", "lbs");

    private String name;
    private String symbol;

    Unit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() { return name; }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, symbol);
    }
}
