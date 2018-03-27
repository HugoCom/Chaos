package iut.chaos.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class User {
    private String id;
    private String email;
    private UserRole role;
    private int score;
    private ArrayList<PrivateMessage> messages;
    private ArrayList<PostComment> comments;
    private ArrayList<Post> posts;

    public User(String id, String email, UserRole role, int score) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.score = score;

        messages = new ArrayList<>();
        comments = new ArrayList<>();
        posts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", score=" + score +
                ", messages=" + messages +
                ", comments=" + comments +
                ", posts=" + posts +
                '}';
    }

    @Exclude
    public String getID() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role.name().toLowerCase();
    }

    public int getScore() {
        return score;
    }
}
