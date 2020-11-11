package space.merunka.cafeorder;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrdersDao {
    @Query("SELECT * FROM orders")
    LiveData<List<Order>> getAllOrders();

    @Insert
    void insertOrder(Order order);

    @Delete
    void deleteOrder(Order order);

    @Query("DELETE FROM orders")
    void deleteAllOrders();
}

