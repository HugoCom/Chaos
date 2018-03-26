package iut.chaos.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import iut.chaos.R;
import iut.chaos.model.DataManager;
import iut.chaos.model.User;
import iut.chaos.model.UserRole;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText inputEmail, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
    }

    public void signUp(View view) {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter your e-mail address.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, should be at least 6 characters.", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(SignUp.this, "Couldn't register user in database because he's not logged in.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUp.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // TODO: Bouton vers SignIn

    // Check if all the data are good, and then create an account
    /*@SuppressLint("SetTextI18n")
    public void createAccount(View view) {
        Boolean mail, passwd, checkbox;
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        TextView wrongU = findViewById(R.id.wrongU);
        TextView wrongP = findViewById(R.id.wrongP);
        TextView wrongC = findViewById(R.id.wrongC);
        CheckBox terms = findViewById(R.id.checkboxConditions);

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username.getText()).matches()) {
            wrongU.setText(R.string.errorSignInEmail);
            mail = false;
        }
        else {
            wrongU.setText(null);
            mail = true;
        }

        if (password.getText().length() < 8) {
            wrongP.setText(R.string.errorSignInPassword);
            passwd = false;
        }
        else {
            wrongP.setText(null);
            passwd = true;
        }

        if (!terms.isChecked()) {
            wrongC.setText(R.string.errorSignInCheckbox);
            checkbox = false;
        }
        else {
            wrongC.setText(null);
            checkbox = true;
        }

        if(mail && passwd && checkbox) {
            // Créer un compte
            User u = new User(username.getText().toString(), UserRole.Member, password.getText().toString());
            Log.i("MYTAG", u.toString());
        }
    }

    // Show Terms of service
    public void showTerms(View view) {
        TextView terms = findViewById(R.id.termsOfServices);
        if(!terms.getText().toString().equals(""))
            terms.setText("");
        else terms.setText(R.string.terms);
    }*/
}
