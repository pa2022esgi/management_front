package main.services;

import main.models.User;
import org.json.JSONObject;

public class AuthService {
    private User user;
    private final static AuthService INSTANCE = new AuthService();

    private AuthService() {}

    public static AuthService getInstance() {
        return INSTANCE;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
