package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage menu) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        menu.setTitle("MENU");
        menu.setScene(new Scene(root, 500, 500));
        menu.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
