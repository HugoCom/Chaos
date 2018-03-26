package iut.chaos.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManager {

    private static DataManager uniqueInstance;
    private DatabaseReference database;
    private User user;

    private String tmpEmail;
    private UserRole tmpRole;
    private int tmpScore;

    private List<Post> posts = new ArrayList<>();

    private DataManager() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * If instance don't exist, create one, else return the instance. [SINGLETON]
     * @return return instance of DataManager
     */
    public static DataManager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DataManager();
        }
        return uniqueInstance;
    }

    public DatabaseReference getDatabase() {
        return database;
    }

    public boolean isUserLogged() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public User getLoggedUser() {
        return user;
    }

    public void removeLoggedUser() {
        user = null;
    }

    public Post createPost(String text, User author) {
        return new PostText(author, ServerValue.TIMESTAMP, text);
    }

    public PostComment createComment(String text, User author, Post origin) {
        PostComment c = new PostComment(author, ServerValue.TIMESTAMP, origin, text);
        origin.addComment(c);
        return c;
    }

    public void writePost(Post p) {
        database.child("posts").child(p.getID()).setValue(p);
    }

    public void writeComment(PostComment c) {
        database.child("comments").child(c.getID()).setValue(c);
    }

    public void registerLoggedUser() throws Exception {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

        if (fbUser == null) {
            throw new Exception("User is not logged in.");
        }

        User user = new User(fbUser.getUid(), fbUser.getEmail(), UserRole.Member, 0);
        database.child("users").child(user.getID()).setValue(user);
        this.user = user;
    }

    public void readLoggedUser(final FirebaseUser fbUser) {
        DatabaseReference ref = database.child("users/" + fbUser.getUid());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    switch (postSnapshot.getKey()) {
                        case "email":
                            tmpEmail = postSnapshot.getValue(String.class);
                            break;
                        case "role":
                            switch (postSnapshot.getValue(String.class)) {
                                case "moderator":
                                    tmpRole = UserRole.Moderator;
                                    break;
                                case "member":
                                    tmpRole = UserRole.Member;
                                    break;
                                case "restricted":
                                    tmpRole = UserRole.Restricted;
                                    break;
                                case "banned":
                                    tmpRole = UserRole.Banned;
                                    break;
                            }
                            break;
                        case "score":
                            tmpScore = postSnapshot.getValue(Integer.class);
                    }
                }
                user = new User(fbUser.getUid(), tmpEmail, tmpRole, tmpScore);
                tmpEmail = null;
                tmpRole = null;
                tmpScore = 0;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("BDReadUser", "Couldn't read user with id: " + fbUser.getUid());
            }
        });
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post p) {
        posts.add(p);
    }

    public void getPostPublicationDateByID(final String id) {
        DatabaseReference ref = database.child("posts/" + id + "/timestamp");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                if (value != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date d = new Date(value);
                    Log.d("BDReadTimestamp", id + " posted on: " + sdf.format(d));
                } else {
                    Log.w("BDReadTimestamp", "Couldn't find post: " + id);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("BDReadTimestamp", "Failed to read value.", error.toException());
            }
        });
    }
}
