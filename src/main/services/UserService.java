package main.services;

import org.json.JSONObject;

public class UserService {
    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMjcuMC4wLjE6ODAwMFwvYXBpXC9sb2dpbiIsImlhdCI6MTY1NDQ0MTEwMywiZXhwIjoxNjU0NDQ0NzAzLCJuYmYiOjE2NTQ0NDExMDMsImp0aSI6IldTVFZoYTV5NjhyaFk4aGEiLCJzdWIiOjEsInBydiI6IjIzYmQ1Yzg5NDlmNjAwYWRiMzllNzAxYzQwMDg3MmRiN2E1OTc2ZjcifQ.U5yIqpSojVLvONsZlYQkIUfOd6HjGF3PjGBJOlBf6XE";
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
