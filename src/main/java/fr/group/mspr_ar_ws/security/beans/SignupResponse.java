package fr.group.mspr_ar_ws.security.beans;

import java.util.ArrayList;
import java.util.List;

public class SignupResponse {
    private String message;
    private String token;

    private List<String> extras = new ArrayList<>();

    public SignupResponse(String message) {
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