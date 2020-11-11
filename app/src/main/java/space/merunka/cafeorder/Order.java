package space.merunka.cafeorder;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class Order {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String drinkTitle;
    private String drinkType;
    private String drinkAddings;
    private int drinkCount;

    public Order(int id, String drinkTitle, String drinkType, String drinkAddings, int drinkCount) {
        this.id = id;
        this.drinkTitle = drinkTitle;
        this.drinkType = drinkType;
        this.drinkAddings = drinkAddings;
        this.drinkCount = drinkCount;
    }

    @Ignore
    public Order(String drinkTitle, String drinkType, String drinkAddings, int drinkCount) {
        this.drinkTitle = drinkTitle;
        this.drinkType = drinkType;
        this.drinkAddings = drinkAddings;
        this.drinkCount = drinkCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrinkTitle() {
        return drinkTitle;
    }

    public void setDrinkTitle(String drinkTitle) {
        this.drinkTitle = drinkTitle;
    }

    public String getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(String drinkType) {
        this.drinkType = drinkType;
    }

    public String getDrinkAddings() {
        return drinkAddings;
    }

    public void setDrinkAddings(String drinkAddings) {
        this.drinkAddings = drinkAddings;
    }

    public int getDrinkCount() {
        return drinkCount;
    }

    public void setDrinkCount(int drinkCount) {
        this.drinkCount = drinkCount;
    }
}
