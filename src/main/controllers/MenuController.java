package main.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.models.User;

import java.io.IOException;

public class MenuController {
    @FXML private Button btn_boards;
    @FXML private Button btn_new;
    @FXML private Button btn_logout;
    @FXML private Label label_email;

    @FXML
    public void initialize() {
        Platform.runLater(() -> ScreenController.getInstance().setCurrent(btn_boards.getScene()));
        //label_email.setText("Connecté en tant que : " + User.getInstance().getUser().getString("email"));
    }

    public void logout() throws IOException {
        ScreenController.getInstance().changeScreen("login");
    }

    public void boards_list() {

    }

    public void new_board() throws IOException {
        ScreenController.getInstance().changeScreen("add_project");
    }
}
