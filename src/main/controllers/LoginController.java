package main.controllers;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.models.User;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class LoginController {
    @FXML private Button btn_login;
    @FXML private Button btn_register;
    @FXML private Label label_error;
    @FXML private TextField text_email;
    @FXML private PasswordField text_password;

    @FXML
    public void initialize() {
        Platform.runLater(() -> ScreenController.getInstance().setCurrent(btn_login.getScene()));
    }

    public void login () {
        OkHttpClient client = new OkHttpClient();
        Dotenv dotenv = Dotenv.load();

        RequestBody body = new FormBody.Builder()
            .add("email", text_email.getText())
            .add("password", text_password.getText())
            .build();

        Request request = new Request.Builder()
            .url(dotenv.get("BASE_URL") + "/login")
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {

            if (response.code() == 500) {
                label_error.setText("Une erreur est survenue");
            } else {
                String res = response.body().string();
                JSONObject json = new JSONObject(res);
                String error = "";

                if (response.code() == 422) {
                    if (json.has("password")) {
                        error = json.getJSONArray("password").getString(0);
                    }
                    if (json.has("email")) {
                        error = json.getJSONArray("email").getString(0);
                    }

                    label_error.setText(error);
                } else if (response.code() == 401) {
                    label_error.setText("Verifiez votre email / mot de passe");
                } else {
                    User.getInstance().setToken(json.getString("access_token"));
                    User.getInstance().setUser(json.getJSONObject("user"));

                    ScreenController.getInstance().changeScreen("menu");
                }
            }
        } catch (IOException e) {
            label_error.setText("Une erreur est survenue");
        }
    }

    public void register () throws IOException {
        ScreenController.getInstance().changeScreen("register");
    }
}
