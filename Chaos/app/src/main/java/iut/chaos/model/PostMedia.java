package iut.chaos.model;

public class PostMedia extends Post{
    private static final int MAX_LENGTH = 200;

    private Media media;

    /**
     * Constructor for Post with a media
     * @param media Media of the post
     * @param author Author of the post
     */
    public PostMedia(User author, Object timestamp, Media media, String text) {
        super(author, timestamp, text);
        this.media = media;
    }

    public Media getMedia() {
        return media;
    }
}
