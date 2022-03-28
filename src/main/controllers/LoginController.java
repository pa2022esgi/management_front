package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class LoginController {
    @FXML private Button btn_login;
    @FXML private Button btn_register;

    public void login () {

    }

    public void register () throws IOException {
        btn_register.getScene().setRoot(FXMLLoader.load(getClass().getResource("../ressources/fxml/register.fxml")));
    }
}
