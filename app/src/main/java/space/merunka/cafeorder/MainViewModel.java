package space.merunka.cafeorder;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class MainViewModel extends AndroidViewModel {

    private static OrdersDatabase database;

    private LiveData<List<Order>> orders;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = OrdersDatabase.getInstance(getApplication());
        orders = database.ordersDao().getAllOrders();
    }

    public LiveData<List<Order>> getOrders(){
        return orders;
    }

    public void insertOrder(Order order){
        new InsertTask().execute(order);
    }

    public void deleteOrder(Order order){
        new DeleteTask().execute(order);
    }

    public void deleteAllNotes() {
        new DeleteTask().execute();
    }

    private static class InsertTask extends AsyncTask<Order, Void, Void>{
        @Override
        protected Void doInBackground(Order... orders) {
            if (orders != null && orders.length > 0){
                database.ordersDao().insertOrder(orders[0]);
            }
            return null;
        }
    }


    private static class DeleteTask extends AsyncTask<Order, Void, Void>{
        @Override
        protected Void doInBackground(Order... orders) {
            if (orders != null && orders.length > 0){
                database.ordersDao().deleteOrder(orders[0]);
            }
            return null;
        }
    }


    private static class DeleteAllTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... orders) {
            database.ordersDao().deleteAllOrders();
            return null;
        }
    }

}
