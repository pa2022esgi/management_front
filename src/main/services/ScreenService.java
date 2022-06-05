package main.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.models.Screen;

import java.io.IOException;
import java.util.HashMap;

public class ScreenService {
    private final HashMap<String, Screen> screenMap = new HashMap<>();
    private Scene current;

    private final static ScreenService INSTANCE = new ScreenService();

    private ScreenService() {}

    public static ScreenService getInstance() { return INSTANCE; }

    public void addScreen(String name, int width, int height) {
        String path = "../ressources/fxml/" + name + ".fxml";
        screenMap.put(name, new Screen(path, width, height));
    }

    public Parent loadScreen (String name) throws IOException {
        Screen screen = screenMap.get(name);
        return FXMLLoader.load(getClass().getResource(screen.getPath()));
    }

    public Screen getScreen (String name) {
        return screenMap.get(name);
    }

    public void setCurrent(Scene scene) {
        this.current = scene;
    }

    public void joinProject() {}

    public void changeScreen (String name) throws IOException {
        Screen screen = screenMap.get(name);
        current.setRoot(FXMLLoader.load(getClass().getResource(screen.getPath())));

        if (current.getWindow().getHeight() != screen.getHeight()) {
            current.getWindow().setHeight(screen.getHeight());
        }

        if (current.getWindow().getWidth() != screen.getWidth()) {
            current.getWindow().setWidth(screen.getWidth());
        }
    }
}
