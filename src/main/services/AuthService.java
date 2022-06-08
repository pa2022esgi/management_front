package main.services;

import org.json.JSONObject;

public class AuthService {
    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMjcuMC4wLjE6ODAwMFwvYXBpXC9sb2dpbiIsImlhdCI6MTY1NDcyMzQ0MCwiZXhwIjoxNjU0NzI3MDQwLCJuYmYiOjE2NTQ3MjM0NDAsImp0aSI6ImR6cmxuTm0xNW8yRkh4ZkciLCJzdWIiOjEsInBydiI6IjIzYmQ1Yzg5NDlmNjAwYWRiMzllNzAxYzQwMDg3MmRiN2E1OTc2ZjcifQ.v2DYNjZOvGO38RqhCIamPizTBYeQUyFHcGWxTT8VmcI";
    private JSONObject user;
    private final static AuthService INSTANCE = new AuthService();

    private AuthService() {}

    public static AuthService getInstance() {
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
