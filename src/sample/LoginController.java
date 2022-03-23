package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML private Button btn_login;
    @FXML private Button btn_register;

    public void login () {
        System.out.println("ok");
    }

    public void register () throws IOException {
        Stage actual = (Stage) btn_register.getScene().getWindow();
        actual.setScene(new Scene(FXMLLoader.load(getClass().getResource("register.fxml"))));
    }
}
