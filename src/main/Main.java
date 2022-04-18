package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("ressources/fxml/add_project.fxml"));
        Scene scene = new Scene(root, 700, 750);

        stage.setTitle("Application");
        stage.setScene(scene);
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        stage.centerOnScreen();
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
