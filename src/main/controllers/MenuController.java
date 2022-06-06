package main.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.services.ScreenService;

import java.io.IOException;

public class MenuController {
    @FXML private Button btn_boards;
    @FXML private Button btn_new;
    @FXML private Button btn_logout;
    @FXML private Label label_email;

    @FXML
    public void initialize() {
        Platform.runLater(() -> ScreenService.getInstance().setCurrent(btn_boards.getScene()));
        //label_email.setText("Connecté en tant que : " + User.getInstance().getUser().getString("email"));
    }

    public void logout() throws IOException {
        ScreenService.getInstance().changeScreen("login");
    }

    public void getProjects() throws IOException {
        ScreenService.getInstance().changeScreen("show_projects");
    }

    public void newProject() throws IOException {
        ScreenService.getInstance().changeScreen("add_project");
    }
}
