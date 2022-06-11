package main.models;

import org.json.JSONObject;

public class User {
    String token;
    Integer id;
    String email;

    public User(String token, Integer id, String email) {
        this.token = token;
        this.id = id;
        this.email = email;
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
}
