package space.merunka.cafeorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {

    public List<Order> orderList = new ArrayList<>();
    private OrdersAdapter ordersAdapter;
    private String name;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemMain) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(OrderListActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        TextView textViewName = findViewById(R.id.textViewName);

        RecyclerView recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        ordersAdapter = new OrdersAdapter(orderList);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            name = user.getDisplayName();
        }
        textViewName.setText(name);

        getData();
        recyclerViewOrders.setAdapter(ordersAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
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
        mDatabase.child(name).child(order.toString()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(OrderListActivity.this,
                        "Удалено", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {

        mDatabase.child(name).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Order order = snapshot.getValue(Order.class);
                ordersAdapter.addOrder(order);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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