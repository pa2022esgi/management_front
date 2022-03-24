package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class RegisterController {
    @FXML private Button btn_login;
    @FXML private Button btn_register;

    public void login () throws IOException {
        btn_login.getScene().setRoot(FXMLLoader.load(getClass().getResource("../ressources/fxml/login.fxml")));
    }

    public void register () {

    }
}
