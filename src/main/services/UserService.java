package main.services;

import org.json.JSONObject;

public class UserService {
    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMjcuMC4wLjE6ODAwMFwvYXBpXC9sb2dpbiIsImlhdCI6MTY1NDQ0MjYwNSwiZXhwIjoxNjU0NDQ2MjA1LCJuYmYiOjE2NTQ0NDI2MDUsImp0aSI6InBKM295N1FMWVA0c0kyRzIiLCJzdWIiOjEsInBydiI6IjIzYmQ1Yzg5NDlmNjAwYWRiMzllNzAxYzQwMDg3MmRiN2E1OTc2ZjcifQ.a1WqR26J1Fa50Z-9pvfepjfy1I06Oy8AHQNk0WCbZiY";
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
