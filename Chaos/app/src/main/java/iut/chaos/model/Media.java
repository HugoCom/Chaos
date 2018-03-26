package iut.chaos.model;

public class Media {
    private MediaType type;
    private String url;

    public Media(MediaType type) {
        this.type = type;
    }

    public MediaType getType() {
        return type;
    }
}
