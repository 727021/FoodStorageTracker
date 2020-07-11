package final_project.fstracker.foodstoragetracker;

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

    /**
     * @return The name of a Category as a String.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Find the index of a Category in Category.values()
     *
     * @param category The Category to find the index of
     * @return The index of a Category, or -1
     */
    public static int indexOf(Category category) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i] == category)
                return i;
        }
        return -1;
    }
}
