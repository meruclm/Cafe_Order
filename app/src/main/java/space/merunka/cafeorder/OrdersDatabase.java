package space.merunka.cafeorder;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Order.class}, version = 1, exportSchema = false)
public abstract class OrdersDatabase extends RoomDatabase {

    private static OrdersDatabase database;
    private static final String DB_NAME = "orders.db";
    private static final Object LOCK = new Object();

    public static OrdersDatabase getInstance(Context context){
        synchronized (LOCK){
            if (database == null){
                database = Room.databaseBuilder(context, OrdersDatabase.class, DB_NAME).build();
            }
        }
        return database;
    }

    public abstract OrdersDao ordersDao();
}
