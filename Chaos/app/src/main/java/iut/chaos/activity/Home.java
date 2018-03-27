package iut.chaos.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import iut.chaos.R;
import iut.chaos.adapter.PostAdapter;
import iut.chaos.model.DataManager;
import iut.chaos.model.Post;

public class Home extends AppCompatActivity {

    private PostAdapter adapter;
    private String tmp_postText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        adapter = new PostAdapter(this, R.layout.cell, R.id.textPost, DataManager.getInstance().getPosts());

        DatabaseReference ref = DataManager.getInstance().getDatabase().child("posts/");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if (postSnapshot.getKey().equals("text")) {
                        tmp_postText = postSnapshot.getValue(String.class);
                        break;
                    }
                }
                Post p = DataManager.getInstance().createPost(tmp_postText, DataManager.getInstance().getLoggedUser());
                DataManager.getInstance().addPost(p);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ListView list = findViewById(R.id.listPost);
        list.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!DataManager.getInstance().isUserLogged()) {
            startActivity(new Intent(Home.this, Welcome.class));
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!DataManager.getInstance().isUserLogged()) {
            startActivity(new Intent(Home.this, Welcome.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.account:
                startActivity(new Intent(Home.this, Account.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Not implemented yet, need to use RecyclerView so that likeButton works
     */
    public void likeButton(View view){
        Log.i("MYTAG", "Like Button");
    }

    /**
     * Not implemented yet, need to use RecyclerView so that unlikeButton works
     */
    public void unlikeButton(View view){
        Log.i("MYTAG", "Unlike Button");
    }

    /**
     * Use to add a Post
     */
    public void addPost(View view) {
        startActivity(new Intent(Home.this, CreatePost.class));
    }
}
