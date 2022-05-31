package main.models;

import javafx.scene.Parent;

public class Screen {
    String scene;
    int width;
    int height;

    public Screen(String scene, int width, int height) {
        this.scene = scene;
        this.width = width;
        this.height = height;
    }

    public String getPath() {
        return scene;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
