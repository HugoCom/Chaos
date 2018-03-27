package iut.chaos.model;

import android.location.Location;

public class PostEvent extends Post{
    private static final int MAX_LENGTH = 200;

    private Location eventLocation;

    /**
     * Constructor for a Post with a location
     * @param eventLocation Location for the post
     * @param author Author of the post
     */
    public PostEvent(User author, Object timestamp, String text, Location eventLocation) {
        super(author, timestamp, text);
        this.eventLocation = eventLocation;
    }

    public Location getEventLocation() {
        return eventLocation;
    }
}
