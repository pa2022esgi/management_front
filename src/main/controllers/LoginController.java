package main.controllers;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class LoginController {
    @FXML private Button btn_login;
    @FXML private Button btn_register;
    @FXML private Label label_error;
    @FXML private TextField text_email;
    @FXML private PasswordField text_password;

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
                    System.out.println(json.getString("access_token"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register () throws IOException {
        btn_register.getScene().setRoot(FXMLLoader.load(getClass().getResource("../ressources/fxml/register.fxml")));
    }
}