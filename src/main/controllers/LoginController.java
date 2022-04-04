package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class LoginController {
    @FXML private Button btn_login;
    @FXML private Button btn_register;

    public void login () {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
            .url("http://127.0.0.1:8000/api/test")
            .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register () throws IOException {
        btn_register.getScene().setRoot(FXMLLoader.load(getClass().getResource("../ressources/fxml/register.fxml")));
    }
}
