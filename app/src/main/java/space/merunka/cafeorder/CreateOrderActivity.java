package space.merunka.cafeorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {

    private TextView textView_order_adding;

    private Spinner spinnerTea;
    private Spinner spinnerCoffee;

    private CheckBox milk;
    private CheckBox sugar;
    private CheckBox lemon;

    private String drink;
    private String name;

    private EditText drinkCount;

    private StringBuilder builderAdditions;

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        drink = getString(R.string.tea);

        textView_order_adding = findViewById(R.id.textView_order_adding);
        String adding = String.format(getString(R.string.text_adding_order), drink);
        textView_order_adding.setText(adding);

        spinnerTea = findViewById(R.id.spinner_drink_types_tea);
        spinnerCoffee = findViewById(R.id.spinner_drink_types_coffee);
        milk = findViewById(R.id.checkbox_milk);
        sugar = findViewById(R.id.checkbox_sugar);
        lemon = findViewById(R.id.checkbox_lemon);

        drinkCount = findViewById(R.id.editTextNumber_amount);
        drinkCount.setText("1");

        builderAdditions = new StringBuilder();
    }

    public void OnClickCreateOrder(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if(id == R.id.radio_button_tea){
            drink = getString(R.string.tea);
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCoffee.setVisibility(View.INVISIBLE);
            lemon.setVisibility(View.VISIBLE);
        } else if (id == R.id.radio_button_coffee){
            drink = getString(R.string.coffee);
            spinnerCoffee.setVisibility(View.VISIBLE);
            spinnerTea.setVisibility(View.INVISIBLE);
            lemon.setVisibility(View.INVISIBLE);
        }
        String adding = String.format(getString(R.string.text_adding_order), drink);
        textView_order_adding.setText(adding);
    }

    public void OnClickSendOrder(View view) {
        builderAdditions.setLength(0);
        if (milk.isChecked()){
            builderAdditions.append(getString(R.string.checkbox_milk)).append(" ");
        }
        if (sugar.isChecked()){
            builderAdditions.append(getString(R.string.checkbox_sugar)).append(" ");
        }
        if (lemon.isChecked() && drink.equals(getString(R.string.tea))){
            builderAdditions.append(getString(R.string.checkbox_lemon)).append(" ");
        }

        String drinkType;
        if(drink.equals(getString(R.string.tea))){
            drinkType = spinnerTea.getSelectedItem().toString().toLowerCase();
        } else {
            drinkType = spinnerCoffee.getSelectedItem().toString().toLowerCase();
        }

        String addition;
        if(builderAdditions.length() > 0){
            addition = builderAdditions.toString().toLowerCase();
        } else {
            addition = "";
        }

        int drinks = 1;
        String drinksString = drinkCount.getText().toString();
        if ( !drinksString.isEmpty() && !drinksString.equals("0")){
            drinks = Integer.parseInt(drinksString);
        }

        Order order = new Order(drink, drinkType, addition, drinks);
        mainViewModel.insertOrder(order);
        Intent intent = new Intent(this, OrderListActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}