package iut.chaos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import iut.chaos.R;
import iut.chaos.model.DataManager;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

        if (fbUser != null) {
            DataManager.getInstance().readLoggedUser(fbUser);
            startActivity(new Intent(Welcome.this, Home.class));
            finish();
        }
    }

    /**
     * If the user choose to register, he is redirected on the activity 'SignUp'
     */
    public void register(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    /**
     * If the user chooser to connect, he is redirected on the activity 'SignIn'
     */
    public void connect(View view) {
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }
}
