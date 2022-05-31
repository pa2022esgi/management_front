package main.controllers;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class RegisterController {
    @FXML private Button btn_login;
    @FXML private Button btn_register;
    @FXML private TextField text_email;
    @FXML private PasswordField text_password;
    @FXML private PasswordField text_confirm;
    @FXML private Label label_error;

    @FXML
    public void initialize() {
        Platform.runLater(() -> ScreenController.getInstance().setCurrent(btn_register.getScene()));
    }

    public void login () throws IOException {
        ScreenController.getInstance().changeScreen("login");
    }

    public void register () {
        OkHttpClient client = new OkHttpClient();
        Dotenv dotenv = Dotenv.load();

        RequestBody body = new FormBody.Builder()
                .add("email", text_email.getText())
                .add("password", text_password.getText())
                .add("password_confirmation", text_confirm.getText())
                .build();

        Request request = new Request.Builder()
                .url(dotenv.get("BASE_URL") + "/register")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (response.code() == 500) {
                label_error.setText("Une erreur est survenue");
            } else {
                String res = response.body().string();
                JSONObject json = new JSONObject(res);
                String error = "";

                if (response.code() == 400) {
                    if (json.has("password")) {
                        error = json.getJSONArray("password").getString(0);
                    }
                    if (json.has("email")) {
                        error = json.getJSONArray("email").getString(0);
                    }

                    label_error.setText(error);
                } else {
                    ScreenController.getInstance().changeScreen("login");
                }
            }
        } catch (IOException e) {
            label_error.setText("Une erreur est survenue");
        }
    }
}
