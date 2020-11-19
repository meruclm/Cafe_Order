package space.merunka.cafeorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText editTextMail;
    private EditText editTextPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextMail = findViewById(R.id.editText_enter_mail);
        editTextPassword = findViewById(R.id.editText_enter_password);
        TextView textViewRegister = findViewById(R.id.textView_main_register);
        mAuth = FirebaseAuth.getInstance();

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PreBuiltUIRegister.class);
                startActivity(intent);
            }
        });
    }

    public void OnClickLogin(View view) {
        String email = editTextMail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(!email.isEmpty() && !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(MainActivity.this, OrderListActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, R.string.text_wrong_psw,
                                        Toast.LENGTH_SHORT).show();
                                } }
                    });
        } else {
            Toast.makeText(this, R.string.text_warning, Toast.LENGTH_SHORT).show();
        }
    }
}

//ADD DATABASE INSTEAD OF CLOUD FIRESTORE
//DELETE ORDER
//SIGN OUT