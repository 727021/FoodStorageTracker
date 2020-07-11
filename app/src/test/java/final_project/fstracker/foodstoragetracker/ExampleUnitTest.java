package final_project.fstracker.foodstoragetracker;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void isExpiredShouldWork() {
        try {
            FoodItem f = new FoodItem();
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            Date d = format.parse("06/11/2019");
            f.setExpirationDate(d);
            assertTrue(f.isExpired());

            d = format.parse("06/13/2019");
            f.setExpirationDate(d);
            assertFalse(f.isExpired());

            f.setExpirationDate(format.parse("06/12/2019"));
            assertTrue(f.isExpired());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void itemShouldBeSaved() {
        assertNotNull(StorageManager.getLocalStorage());

        FoodItem f = new FoodItem();
        f.setName("Rice");
        f.setCategory(Category.CAT_8);
        f.setUnits(Unit.KILOGRAM);
        f.setExpirationDate(new Date());
        f.setQuantity(25);

        int i = StorageManager.getLocalStorage().saveItem(f);
        assertEquals(1, i);
    }

    @Test
    public void itemShouldBeDeleted() {
        assertNotNull(StorageManager.getLocalStorage());

        FoodItem f = new FoodItem();
        f.setName("Name");
        f.setCategory(Category.CAT_2);
        f.setUnits(Unit.CUP);
        f.setExpirationDate(new Date());
        f.setQuantity(10);

        int i = StorageManager.getLocalStorage().saveItem(f);
        assertEquals(1, i);

        i = StorageManager.getLocalStorage().deleteItem(f);
        assertEquals(1, i);
    }
}