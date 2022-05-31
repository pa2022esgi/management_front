package main.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AddProjectController {
    @FXML private Button btn_menu;

    @FXML
    public void initialize() {
        Platform.runLater(() -> ScreenController.getInstance().setCurrent(btn_menu.getScene()));
    }

    public void backToMenu () throws IOException {
        ScreenController.getInstance().changeScreen("menu");
    }
}
