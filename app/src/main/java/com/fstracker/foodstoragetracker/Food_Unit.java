package com.fstracker.foodstoragetracker;

public enum Food_Unit {
    ALL("All"),
    CAT_2("Cup"),
    CAT_3("Fluid Ounce"),
    CAT_4("Gallon"),
    CAT_5("Gram"),
    CAT_6("Kilogram"),
    CAT_7("Liter"),
    CAT_8("Milligram"),
    CAT_9("Ounces"),
    CAT_10("Pint"),
    CAT_11("Pounds"),
    CAT_12("Quart");



    private String name;

    Food_Unit(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
