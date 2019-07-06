package com.fstracker.foodstoragetracker;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * A class representing an item in someone's food storage.
 */
@Entity(tableName = "foodItems")
public class FoodItem {
    // Used for passing json serialized FoodItem objects between activities
    @Ignore
    public static final transient String EXTRA = "com.fstracker.foodstoragetracker.FOOD_ITEM";

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private Date expirationDate;
    private Category category;
    private Unit units;
    private double quantity;
//
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

    @NonNull
    @Override
    public String toString() {
        return String.format("%.1f %s%s of %s", quantity, units.getName().toLowerCase(), (quantity > 1 && units != Unit.COUNT) ? "s" : "", name);
    }


}
