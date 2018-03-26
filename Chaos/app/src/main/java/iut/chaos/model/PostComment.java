package iut.chaos.model;

public class PostComment extends Post {
    private static final int MAX_LENGTH = 200;

    public PostComment(User author, Object timestamp, Post origin, String text) {
        super(author, timestamp, origin, text);
    }

}
