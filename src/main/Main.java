package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.controllers.ScreenController;
import main.models.Screen;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        addScreens();

        String startScreen = "add_project";

        Parent root = ScreenController.getInstance().loadScreen(startScreen);
        Screen screen = ScreenController.getInstance().getScreen(startScreen);
        Scene scene = new Scene(root, screen.getWidth(), screen.getHeight());
        stage.setTitle("Application");
        stage.setScene(scene);
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        stage.centerOnScreen();
        stage.show();
    }

    public void addScreens() {
        ScreenController.getInstance().addScreen("menu", 0, 500, 500);
        ScreenController.getInstance().addScreen("add_project", 1, 700, 825);
        ScreenController.getInstance().addScreen("login", 2, 500, 500);
        ScreenController.getInstance().addScreen("register", 3, 500, 500);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
