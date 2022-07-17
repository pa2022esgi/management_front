package main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.models.User;
import main.services.AuthService;
import main.services.ImageService;
import main.services.ScreenService;
import main.models.Screen;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException{
        AuthService.getInstance().setUser(new User(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMjcuMC4wLjE6ODAwMFwvYXBpXC9sb2dpbiIsImlhdCI6MTY1ODA0OTIyNSwiZXhwIjoxNjU4MTM1NjI1LCJuYmYiOjE2NTgwNDkyMjUsImp0aSI6IkxDWGFjcVhVZUVYOEhsV00iLCJzdWIiOjEsInBydiI6IjIzYmQ1Yzg5NDlmNjAwYWRiMzllNzAxYzQwMDg3MmRiN2E1OTc2ZjcifQ.WAn1MnUSbwmcA1re_SQy0od66FN9zuzZ87OCzaInHsk",
                1,
                "test@mail.com",
                null
        ));

        addScreens();
        addImages();

        String startScreen = "menu";

        Parent root = ScreenService.getInstance().loadScreen(startScreen);
        Screen screen = ScreenService.getInstance().getScreen(startScreen);
        Scene scene = new Scene(root, screen.getWidth(), screen.getHeight());
        stage.setTitle("Management");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/main/ressources/icon/logo.png")));
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
        ScreenService.getInstance().addScreen("handle_task", 900, 440);
        ScreenService.getInstance().addScreen("plugins", 600, 400);
    }

    public void addImages() {
        ImageService.getInstance().addImage("icon_del", "/main/ressources/icon/delete.png");
        ImageService.getInstance().addImage("icon_eye", "/main/ressources/icon/eye.png");
        ImageService.getInstance().addImage("icon_edit", "/main/ressources/icon/edit.png");
        ImageService.getInstance().addImage("icon_add_card", "/main/ressources/icon/add_card.png");
        ImageService.getInstance().addImage("icon_admin", "/main/ressources/icon/admin.png");
        ImageService.getInstance().addImage("icon_pdf", "/main/ressources/icon/pdf.png");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
