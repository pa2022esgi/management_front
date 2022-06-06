package main.models;

import org.json.JSONObject;

public class Project {
    String name;
    String description;
    String token;

    public Project(JSONObject json) {
        this.name = json.getString("name");
        this.token = json.getString("token");
        this.description = json.isNull("description") ? "Aucune description" : json.getString("description");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getToken() {
        return token;
    }
}
