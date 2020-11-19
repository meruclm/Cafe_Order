package space.merunka.cafeorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView name;
    private TextView mail;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.editText_register_name);
        mail = findViewById(R.id.editText_register_mail);
        password = findViewById(R.id.editText_register_password);
    }

    public void OnClickRegisterNewUser(View view) {
        String newMail = mail.getText().toString().trim();
        String newPassword = password.getText().toString().trim();
        final String newName = name.getText().toString().trim();

        if (!newMail.isEmpty() && !newPassword.isEmpty() && !newName.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(newMail, newPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(newName)
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(RegisterActivity.this,
                                                        e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                Intent intent = new Intent(RegisterActivity.this, OrderListActivity.class);
                                startActivity(intent);
                                Toast.makeText(RegisterActivity.this, R.string.text_user_registered,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, R.string.text_error,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else{
            Toast.makeText(RegisterActivity.this, R.string.text_warning, Toast.LENGTH_SHORT).show();
        }
    }

}