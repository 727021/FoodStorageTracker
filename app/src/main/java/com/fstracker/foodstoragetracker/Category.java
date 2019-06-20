package com.fstracker.foodstoragetracker;

public enum Category {
    // Temporary categories (these will change)
    ALL("All"),
    CAT_2("Category 2"),
    CAT_3("Category 3"),
    CAT_4("Category 4"),
    CAT_5("Category 5");

    private String name;

    Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
