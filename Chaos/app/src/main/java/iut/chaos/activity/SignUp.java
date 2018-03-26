package iut.chaos.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import iut.chaos.R;
import iut.chaos.model.DataManager;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText inputEmail, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
    }

    public void signUp(View view) {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), R.string.toast_email, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), R.string.toast_password, Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), R.string.toast_password_length, Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            try {
                                DataManager.getInstance().registerLoggedUser();
                                finish();
                            } catch (Exception e) {
                                Toast.makeText(SignUp.this, R.string.toast_user_not_logged, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else Toast.makeText(SignUp.this, getString(R.string.toast_authentification_failed) + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void signInRedirection(View view) {
        startActivity(new Intent(SignUp.this, SignIn.class));
    }

    //TODO: Terms of Service

}
