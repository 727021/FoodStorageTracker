package final_project.fstracker.foodstoragetracker;

import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentTest {
    @Test
    public void fillDatabase() {
        String[] names = {
                "Apples", "Bananas", "Peaches", "Corn", "Carrots", "Potatoes",
                "Water", "Milk", "Juice", "Rice", "Flour", "Sugar"
        };

        // Clear the database first
        StorageManager.getLocalStorage().deleteAllItems(StorageManager.getLocalStorage().getAllItems());
        assertNotNull(StorageManager.getLocalStorage());

        // Insert 100 items
        for (int i = 0; i < 100; i++) {
            FoodItem item = new FoodItem();
            item.setName(names[new Random().nextInt(names.length)]);
            item.setCategory(Category.values()[new Random().nextInt(Category.values().length)]);
            item.setQuantity(new Random().nextInt(100));
            item.setUnits(Unit.values()[new Random().nextInt(Unit.values().length)]);
            item.setExpirationDate(new Date());
            StorageManager.getLocalStorage().saveItem(item);
        }
    }
}
