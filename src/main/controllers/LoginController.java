package main.controllers;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class LoginController {
    @FXML private Button btn_login;
    @FXML private Button btn_register;
    @FXML private Label label_error;

    public void login () {
        OkHttpClient client = new OkHttpClient();
        Dotenv dotenv = Dotenv.load();

        Request request = new Request.Builder()
            .url(dotenv.get("BASE_URL") + "/test")
            .build();

        try (Response response = client.newCall(request).execute()) {

            if (response.code() == 500) {
                label_error.setText("Une erreur est survenue");
            } else {

                JSONObject json = new JSONObject(response.body().string());

                if (response.code() != 200) {
                    label_error.setText(json.getString("message"));
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
