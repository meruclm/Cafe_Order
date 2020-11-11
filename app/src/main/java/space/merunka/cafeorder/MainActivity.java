package space.merunka.cafeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextName = findViewById(R.id.editText_name);
        editTextPassword = findViewById(R.id.editText_psw);
    }

    public void OnClickLogin(View view) {
        String name = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(!name.isEmpty() && !password.isEmpty()){
            if (password.equals("2020")) {
                Intent intent = new Intent(this, OrderListActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.text_wrong_psw, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.text_warning, Toast.LENGTH_SHORT).show();
        }
    }
}

//ЗАНЕСТИ ЧЕК-БОКС В БАЗУ ДАННЫХ (IF NEEDED)