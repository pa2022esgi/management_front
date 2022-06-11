package main.models;

import org.json.JSONObject;

public class User {
    String token;
    Integer id;
    String email;
    boolean banished;

    public User(String token, Integer id, String email, boolean banished) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.banished = banished;
    }

    public String getToken() {
        return token;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean isBanished() {
        return banished;
    }

    public void setBanished(boolean banished) {
        this.banished = banished;
    }
}
