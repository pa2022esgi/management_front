package main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.services.ScreenService;
import main.models.Screen;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        addScreens();

        String startScreen = "add_project";

        Parent root = ScreenService.getInstance().loadScreen(startScreen);
        Screen screen = ScreenService.getInstance().getScreen(startScreen);
        Scene scene = new Scene(root, screen.getWidth(), screen.getHeight());
        stage.setTitle("Application");
        stage.setScene(scene);
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        stage.centerOnScreen();
        stage.show();
    }

    public void addScreens() {
        ScreenService.getInstance().addScreen("menu", 0, 500, 500);
        ScreenService.getInstance().addScreen("add_project", 1, 700, 825);
        ScreenService.getInstance().addScreen("login", 2, 500, 500);
        ScreenService.getInstance().addScreen("register", 3, 500, 500);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
