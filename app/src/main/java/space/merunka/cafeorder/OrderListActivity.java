package space.merunka.cafeorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {

    public final List<Order> orderList = new ArrayList<>();
    private OrdersAdapter ordersAdapter;
    private String name;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            name = user.getDisplayName();
        }

        TextView textViewName = findViewById(R.id.textViewName);
        textViewName.setText(name);

        RecyclerView recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        ordersAdapter = new OrdersAdapter(orderList);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        getData();
        recyclerViewOrders.setAdapter(ordersAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Order order = ordersAdapter.getOrders().get(viewHolder.getAdapterPosition());
                ordersAdapter.deleteOrder(order);
                deleteOrderFromDb(order);

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewOrders);
    }

    public void OnClickCreateNewOrder(View view) {
        Intent intent = new Intent(this, CreateOrderActivity.class);
        startActivity(intent);
    }

    private void deleteOrderFromDb(Order order){
        db.collection("orders")


                .document(String.valueOf(order)).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(OrderListActivity.this,
                                "Удалено", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OrderListActivity.this,
                                "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void getData() {
        db.collection(name)
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value != null){
                        List<Order> orders = value.toObjects(Order.class);
                        ordersAdapter.setOrders(orders);
                    }
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