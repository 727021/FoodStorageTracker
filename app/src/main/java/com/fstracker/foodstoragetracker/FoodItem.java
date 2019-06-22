package com.fstracker.foodstoragetracker;

import java.util.Date;

public class FoodItem {
    public static final transient String EXTRA = "com.fstracker.foodstoragetracker.FOOD_ITEM";

    private int id;
    private String name;
    private Date expirationDate;
    private Category category;
    private Unit units;
    private double quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isExpired() {
        return new Date().after(expirationDate);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Unit getUnits() {
        return units;
    }

    public void setUnits(Unit units) {
        this.units = units;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
