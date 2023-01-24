package fr.group.mspr_ar_ws.models;

import java.util.ArrayList;
import java.util.List;

public class MessageResponse {
    private String message;
    private String token;

    private List<String> extras = new ArrayList<>();

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addExtra(String extras) {
        this.extras.add(extras);
    }
    public void removeExtra(String extras) {
        this.extras.remove(extras);
    }
}