package com.fstracker.foodstoragetracker;

/**
 * An enumeration of categories that FoodItems can belong to.
 */
public enum Category {
    // Temporary categories (these will change)
    ALL("All"),
    CAT_2("Baking Mixes"),
    CAT_3("Can"),
    CAT_4("Cereals"),
    CAT_5("Dairy"),
    CAT_6("Drink Mixes"),
    CAT_7("Fruits"),
    CAT_8("Grains & Flours"),
    CAT_9("Meats & Proteins"),
    CAT_10("Produce"),
    CAT_11("Vegetables");



    private String name;

    Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
