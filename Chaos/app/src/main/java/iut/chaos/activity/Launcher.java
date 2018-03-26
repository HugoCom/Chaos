package iut.chaos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import iut.chaos.R;
import iut.chaos.model.*;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is logged in.
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

        if (fbUser != null) {
            DataManager.getInstance().readLoggedUser(fbUser);
            startActivity(new Intent(Launcher.this, Home.class));
            finish();
        } else {
            startActivity(new Intent(Launcher.this, Welcome.class));
            finish();
        }

        // Testing Database
        /*DataManager dataManager = DataManager.getInstance();
        User u = dataManager.createUser("example@domain.com", "alpine");
        dataManager.registerUser(u);

        iut.chaos.model.Post p = dataManager.createPost("Hello, world!", u);
        dataManager.writePost(p);

        PostComment c = dataManager.createComment("FIRST!!", u, p);
        PostComment c2 = dataManager.createComment("Second!", u, p);
        PostComment c3 = dataManager.createComment("Third!", u, p);
        dataManager.writeComment(c);
        dataManager.writeComment(c2);
        dataManager.writeComment(c3);

        dataManager.writePost(p);

        dataManager.getPostPublicationDateByID(p.getID());*/
    }
}