package space.merunka.cafeorder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import java.util.Collections;
import java.util.List;

public class PreBuiltUIRegister extends AppCompatActivity {

    //FOR FUTURE

    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_built_u_i_register);

        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(PreBuiltUIRegister.this, OrderListActivity.class);
                startActivity(intent);
            } else {
                if (response != null) {
                    Toast.makeText(this,
                            response.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}