package iut.chaos.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import iut.chaos.R;
import iut.chaos.model.DataManager;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText inputEmail, inputPassword;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        btnSignIn = findViewById(R.id.btn_signIn);
    }

    /**
     * Check if email & password are not empty, password is at least 6 characters and then sign in the user
     * Show Toast if there are some errors
     */
    public void signIn(View view) {
        String email = inputEmail.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();

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

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                            DataManager.getInstance().readLoggedUser(fbUser);
                            finish();
                        } else {
                            Toast.makeText(SignIn.this, getString(R.string.toast_authentication_failed) + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void forgotPassword(View v) {
        startActivity(new Intent(SignIn.this, ForgotPassword.class));
    }

    public void signUpRedirection(View view) {
        startActivity(new Intent(SignIn.this, SignUp.class));
        finish();
    }
}
