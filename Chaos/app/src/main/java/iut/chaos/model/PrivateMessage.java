package iut.chaos.model;

public class PrivateMessage {
    private String message;

    public PrivateMessage(String message){
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {

        return message;
    }
}
