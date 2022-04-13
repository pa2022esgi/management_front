package main.models;

public class User {
    private String token;
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
}
