package main.models;

public class ProjectLabel {
    String name;
    String color;

    public ProjectLabel(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}