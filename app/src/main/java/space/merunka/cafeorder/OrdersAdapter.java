package space.merunka.cafeorder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>{

    private List<Order> orders;

    public OrdersAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.textViewDrinkTitle.setText(order.getDrinkTitle());
        holder.textViewDrinkType.setText(order.getDrinkType());
        holder.textViewDrinkAddings.setText(order.getDrinkAddings());
        holder.textViewDrinkCount.setText(String.format("%s", order.getDrinkCount()));
        int colorId;
        String drink = order.getDrinkTitle();
        switch (drink.toLowerCase()){
            case "чай":
                colorId = holder.itemView.getResources().getColor(R.color.color_light_brown);
                break;
            case "кофе":
                colorId = holder.itemView.getResources().getColor(R.color.color_dark_brown);
                break;
            default:
                colorId = holder.itemView.getResources().getColor(R.color.color_white);
        }
        holder.textViewDrinkTitle.setBackgroundColor(colorId);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrdersViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewDrinkTitle;
        private TextView textViewDrinkType;
        private TextView textViewDrinkAddings;
        private TextView textViewDrinkCount;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDrinkTitle = itemView.findViewById(R.id.textViewOrderTitle);
            textViewDrinkType = itemView.findViewById(R.id.textViewOrderDrinkType);
            textViewDrinkAddings = itemView.findViewById(R.id.textViewOrderAddings);
            textViewDrinkCount= itemView.findViewById(R.id.textViewOrderAmount);
        }
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    public List<Order> getOrders() {
        return orders;
    }
}
