package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.models.User;

public class MenuController {
    @FXML private Button btn_boards;
    @FXML private Button btn_new;
    @FXML private Button btn_logout;

    public void logout() {
        System.out.println(User.getInstance().getToken());
    }

    public void boards_list() {

    }

    public void new_board() {

    }
}
