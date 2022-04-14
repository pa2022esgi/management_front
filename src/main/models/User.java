package main.models;

import org.json.JSONObject;

public class User {
    private String token;
    private JSONObject user;
    private final static User INSTANCE = new User();

    private User() {}

    public static User getInstance() {
        return INSTANCE;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setUser(JSONObject user) {
        this.user = user;
    }

    public JSONObject getUser() {
        return this.user;
    }
}
