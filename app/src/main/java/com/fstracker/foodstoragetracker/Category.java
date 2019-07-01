package com.fstracker.foodstoragetracker;

/**
 * An enumeration of categories that FoodItems can belong to.
 */
public enum Category {
    ALL("All"),
    CAN("Canned Foods"),
    CEREAL("Cereals"),
    DAIRY("Dairy"),
    DRINK("Drinks"),
    FRUIT("Fruits"),
    GRAIN("Grains & Flours"),
    MEAT("Meats & Proteins"),
    PRODUCE("Produce"),
    MISC("Miscellaneous");

    private String name;

    Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
