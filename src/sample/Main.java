package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage login) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));

        login.setTitle("Connexion");
        login.setScene(new Scene(root, 500, 500));
        login.setMinWidth(500);
        login.setMinHeight(500);
        login.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
