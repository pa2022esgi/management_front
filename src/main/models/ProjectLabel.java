package main.models;

public class ProjectLabel {
    String name;
    String color;
    Integer id;

    public ProjectLabel(String name, String color, Integer id) {
        this.name = name;
        this.color = color;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Integer getId() {
        return id;
    }
}