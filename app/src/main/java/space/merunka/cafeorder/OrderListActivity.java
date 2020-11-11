package space.merunka.cafeorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {

    private TextView textViewName;
    private RecyclerView recyclerViewOrders;
    public final List<Order> orderList = new ArrayList<>();
    private OrdersAdapter ordersAdapter;
    private MainViewModel mainViewModel;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        textViewName = findViewById(R.id.textViewName);
        textViewName.setText(name);

        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        ordersAdapter = new OrdersAdapter(orderList);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        getData();
        recyclerViewOrders.setAdapter(ordersAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewOrders);
    }

    private void remove(int position) {
        Order order = ordersAdapter.getOrders().get(position);
        mainViewModel.deleteOrder(order);
    }

    public void OnClickCreateNewOrder(View view) {
        Intent intent = new Intent(this, CreateOrderActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    private void getData() {
        LiveData<List<Order>> ordersFromDB = mainViewModel.getOrders();
        ordersFromDB.observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(@Nullable List<Order> orderFromLiveData) {
                ordersAdapter.setOrders(orderFromLiveData);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}