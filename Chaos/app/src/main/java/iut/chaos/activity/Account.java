package iut.chaos.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import iut.chaos.R;
import iut.chaos.model.DataManager;

public class Account extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (!DataManager.getInstance().isUserLogged()) {
            finish();
        }

        TextView loggedAs = findViewById(R.id.loggedAs);
        loggedAs.setText(user.getEmail());
    }

    public void signOut(View v) {
        auth.signOut();
        DataManager.getInstance().removeLoggedUser();
        finish();
    }
}
