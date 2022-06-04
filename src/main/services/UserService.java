package main.services;

import org.json.JSONObject;

public class UserService {
    private String token;
    private JSONObject user;
    private final static UserService INSTANCE = new UserService();

    private UserService() {}

    public static UserService getInstance() {
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
