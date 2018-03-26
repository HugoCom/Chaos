package iut.chaos.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import iut.chaos.R;
import iut.chaos.model.DataManager;
import iut.chaos.model.User;
import iut.chaos.model.UserRole;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText inputEmail, inputPassword;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnSignIn = (Button) findViewById(R.id.btn_signIn);
    }

    public void signIn(View view) {
        String email = inputEmail.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(SignIn.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void forgotPassword(View v) {
        startActivity(new Intent(SignIn.this, ForgotPassword.class));
    }

    // TODO: Bouton vers SignUp

    // Check if the user exist, and then log him
    /*public void connect(View view) {
        EditText username = findViewById(R.id.signInMail);
        EditText password = findViewById(R.id.signInPasswd);
        TextView error = findViewById(R.id.signInError);

        User user = new User(username.getText().toString(), UserRole.Member, password.getText().toString());
        // Need to test if user is in the base, else error
        error.setText(R.string.UserNotInBase);

        Intent intent = new Intent(this, Home.class);
        // On doit passer l'utilisateur dans l'intent
        intent.putExtra("UserID", user.getID());
        startActivity(intent);
    }*/
}
