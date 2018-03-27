package iut.chaos.model;

import android.location.Location;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Post {
    private String id;
    private User author;
    private Post origin;
    private Object timestamp;
    private String text;
    private int score;
    private Location location;
    private ArrayList<PostComment> comments;

    public Post() {
    }

    /**
     * Constructor for PostText
     * @param author Author of the post
     */
    public Post(User author, Object timestamp, String text) {
        this.id = UUID.randomUUID().toString();
        this.author = author;
        this.timestamp = timestamp;
        this.text = text;
        this.score = 0;
        comments = new ArrayList<>();
    }

    public Post(User author, Object timestamp, Post origin, String text) {
        this.id = UUID.randomUUID().toString();
        this.author = author;
        this.timestamp = timestamp;
        this.origin = origin;
        this.text = text;
        this.score = 0;
        comments = new ArrayList<>();
    }

    @Exclude
    public String getID() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Exclude
    public User getAuthor() {
        return author;
    }

    //@PropertyName("author")
    public String getAuthorID() {
        return author.getID();
    }

    public Object getTimestamp() {
        return timestamp;
    }

    @Exclude
    public Post getOrigin() {
        return origin;
    }

    //@PropertyName("origin")
    public String getOriginID() {
        if (origin != null) {
            return origin.getID();
        }
        return null;
    }

    public int getScore() {
        return score;
    }

    @Exclude
    public ArrayList<PostComment> getComments() {
        return comments;
    }

    //@PropertyName("comments")
    public ArrayList<String> getCommentsID() {
        ArrayList<String> commentsID = new ArrayList<>();
        for (PostComment c : comments) {
            commentsID.add(c.getID());
        }
        return commentsID;
    }

    public void addComment(PostComment c) {
        comments.add(c);
    }
}
