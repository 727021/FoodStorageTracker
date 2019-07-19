package com.fstracker.foodstoragetracker;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.fstracker.foodstoragetracker", appContext.getPackageName());
    }

    @Test
    public void fillStorage() {
        FoodItem f0 = new FoodItem();
        FoodItem f1 = new FoodItem();
        FoodItem f2 = new FoodItem();
        FoodItem f3 = new FoodItem();
        FoodItem f4 = new FoodItem();
        FoodItem f5 = new FoodItem();
        FoodItem f6 = new FoodItem();
        FoodItem f7 = new FoodItem();
        FoodItem f8 = new FoodItem();
        FoodItem f9 = new FoodItem();

        List<FoodItem> items  = new ArrayList<>();

        f0.setName("Water");
        f0.setCategory(Category.DRINK);
        f0.setQuantity(50);
        f0.setUnits(Unit.GALLON);
        items.add(f0);

        f1.setName("Apples");
        f1.setCategory(Category.FRUIT);
        f1.setQuantity(5);
        f1.setUnits(Unit.POUND);
        items.add(f1);

        f2.setName("Rice");
        f2.setCategory(Category.GRAIN);
        f2.setQuantity(50);
        f2.setUnits(Unit.KILOGRAM);
        items.add(f2);

        f3.setName("Flour");
        f3.setCategory(Category.GRAIN);
        f3.setQuantity(50);
        f3.setUnits(Unit.POUND);
        items.add(f3);

        f4.setName("Peaches");
        f4.setCategory(Category.FRUIT);
        f4.setQuantity(25);
        f4.setUnits(Unit.POUND);
        items.add(f4);

        f5.setName("Potatoes");
        f5.setCategory(Category.PRODUCE);
        f5.setQuantity(30);
        f5.setUnits(Unit.COUNT);
        items.add(f5);

        f6.setName("Corn");
        f6.setCategory(Category.PRODUCE);
        f6.setQuantity(4);
        f6.setUnits(Unit.LITER);
        items.add(f6);

        for (FoodItem item : items) {
            item.setExpirationDate(new Date());
        }

        int i = StorageManager.getLocalStorage().saveAllItems(items);
        assertEquals(i, items.size());

    }
}
