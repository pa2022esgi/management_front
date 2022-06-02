package main.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.models.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ScreenService {
    private final HashMap<String, Integer> screenMap = new HashMap<>();
    private final ArrayList<Screen> screens = new ArrayList<>();
    private Scene current;

    private final static ScreenService INSTANCE = new ScreenService();

    private ScreenService() {}

    public static ScreenService getInstance() {
        return INSTANCE;
    }

    public void addScreen(String name, int index, int width, int height) {
        String path = "../ressources/fxml/" + name + ".fxml";
        screens.add(new Screen(path, width, height));
        screenMap.put(name, index);
    }

    public Parent loadScreen (String name) throws IOException {
        Screen screen = screens.get(screenMap.get(name));
        return FXMLLoader.load(getClass().getResource(screen.getPath()));
    }

    public Screen getScreen (String name) {
        return screens.get(screenMap.get(name));
    }

    public void setCurrent(Scene scene) {
        this.current = scene;
    }

    public void changeScreen (String name) throws IOException {
        Screen screen = screens.get(screenMap.get(name));
        current.setRoot(FXMLLoader.load(getClass().getResource(screen.getPath())));

        if (current.getWindow().getHeight() != screen.getHeight()) {
            current.getWindow().setHeight(screen.getHeight());
        }

        if (current.getWindow().getWidth() != screen.getWidth()) {
            current.getWindow().setWidth(screen.getWidth());
        }
    }
}
