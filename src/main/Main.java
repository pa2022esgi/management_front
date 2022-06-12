package main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.models.User;
import main.services.AuthService;
import main.services.ImageService;
import main.services.ScreenService;
import main.models.Screen;

import java.io.IOException;

public class Main extends Application {

    @Override
        public void start(Stage stage) throws IOException {

            AuthService.getInstance().setUser(new User(
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMjcuMC4wLjE6ODAwMFwvYXBpXC9sb2dpbiIsImlhdCI6MTY1NDk2MTE3NCwiZXhwIjoxNjU1MDQ3NTc0LCJuYmYiOjE2NTQ5NjExNzQsImp0aSI6Ik04NG9RcTV5Nkd6Q1YwTDgiLCJzdWIiOjEsInBydiI6IjIzYmQ1Yzg5NDlmNjAwYWRiMzllNzAxYzQwMDg3MmRiN2E1OTc2ZjcifQ.eHv8z1DyBHbh6uVqpfrPYYIGDPWr_npVgOzetBCkDJU",
                    1,
                    "test@mail.com",
                    null
            ));

            addScreens();
            addImages();

            String startScreen = "show_projects";

            Parent root = ScreenService.getInstance().loadScreen(startScreen);
            Screen screen = ScreenService.getInstance().getScreen(startScreen);
            Scene scene = new Scene(root, screen.getWidth(), screen.getHeight());
            stage.setTitle("Application");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        }

    public void addScreens() {
        ScreenService.getInstance().addScreen("menu", 500, 500);
        ScreenService.getInstance().addScreen("add_project", 700, 825);
        ScreenService.getInstance().addScreen("edit_project", 700, 850);
        ScreenService.getInstance().addScreen("login", 500, 500);
        ScreenService.getInstance().addScreen("register", 500, 500);
        ScreenService.getInstance().addScreen("show_projects", 1280, 760);
        ScreenService.getInstance().addScreen("add_task", 900, 440);
    }

    public void addImages() {
        ImageService.getInstance().addImage("icon_del", "../ressources/icon/delete.png");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
