package iut.chaos.model;

public class PostText extends Post{
    private static final int MAX_LENGTH = 200;

    public PostText() {
    }

    /**
     * Use to create a Post with text
     * @param text Text which will be displayed on the Post
     * @param author Author of the post
     */
    public PostText(User author, Object timestamp, String text) {
        super(author, timestamp, text);
    }

}
