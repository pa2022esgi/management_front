package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.models.User;

public class MenuController {
    @FXML private Button btn_boards;
    @FXML private Button btn_new;
    @FXML private Button btn_logout;
    @FXML private Label label_email;

    @FXML
    public void initialize() {
        label_email.setText("Connecté en tant que : " + User.getInstance().getUser().getString("email"));
    }

    public void logout() {

    }

    public void boards_list() {

    }

    public void new_board() {

    }
}
