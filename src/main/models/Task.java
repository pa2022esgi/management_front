package main.models;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Task {
    Integer id;
    String title;
    String description;
    String date;
    User user;
    Integer status;
    private final HashMap<Integer, ProjectLabel> labelsMap = new HashMap<>();

    public Task(JSONObject json) throws ParseException {
        this.id = json.getInt("id");
        this.title = json.getString("title");
        this.description = json.isNull("description") ? "" : json.getString("description");;
        this.date = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(json.getString("due_date")));
        this.user = json.isNull("user") ? null : new User(null, json.getJSONObject("user").getInt("id"), json.getJSONObject("user").getString("email"), null);
        this.status = json.getInt("status_id");

        for (int i=0; i < json.getJSONArray("labels").length(); i++) {
            JSONObject label = json.getJSONArray("labels").getJSONObject(i);
            labelsMap.put(label.getInt("id"), new ProjectLabel(label.getString("name"), label.getString("color"), label.getInt("id")));
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Integer getStatus() {
        return status;
    }

    public HashMap<Integer, ProjectLabel> getLabelsMap() {
        return labelsMap;
    }
}
