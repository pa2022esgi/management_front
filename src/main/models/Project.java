package main.models;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;

public class Project {
    String name;
    String description;
    String token;
    Integer id;
    Integer created_by;
    private final HashMap<Integer, User> usersMap = new HashMap<>();
    private final HashMap<Integer, ProjectLabel> labelsMap = new HashMap<>();
    private final HashMap<Integer, Task> tasksMap = new HashMap<>();

    public Project(JSONObject json) throws ParseException {
        this.id = json.getInt("id");
        this.created_by = json.getInt("created_by");
        this.name = json.getString("name");
        this.token = json.getString("token");
        this.description = json.isNull("description") ? "" : json.getString("description");
        for (int i=0; i < json.getJSONArray("users").length(); i++) {
            JSONObject user = json.getJSONArray("users").getJSONObject(i);
            usersMap.put(user.getInt("id"), new User(null, user.getInt("id"), user.getString("email"), user.getJSONObject("pivot").getInt("banished") == 1));
        }
        for (int i=0; i < json.getJSONArray("labels").length(); i++) {
            JSONObject label = json.getJSONArray("labels").getJSONObject(i);
            labelsMap.put(label.getInt("id"), new ProjectLabel(label.getString("name"), label.getString("color"), label.getInt("id")));
        }
        for (int i = 0; i < json.getJSONArray("cards").length(); i++) {
            JSONObject task = json.getJSONArray("cards").getJSONObject(i);
            tasksMap.put(task.getInt("id"), new Task(task));
        }
    }

    public String getName() {
        return name;
    }

    public Integer getAuthor() {
        return created_by;
    }

    public String getDescription() {
        return description;
    }

    public String getToken() {
        return token;
    }

    public HashMap<Integer, User> getUsersMap() {
        return usersMap;
    }

    public HashMap<Integer, ProjectLabel> getLabelsMap() {
        return labelsMap;
    }

    public Integer getId() {
        return id;
    }

    public HashMap<Integer, Task> getTasksMap() {
        return tasksMap;
    }
}
