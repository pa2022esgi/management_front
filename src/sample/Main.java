package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        Label emailLabel = new Label("Email");
        TextField emailInput = new TextField();
        emailInput.setPrefWidth(300);

        Label pwdLabel = new Label("Password");
        TextField pwdInput = new TextField();
        pwdInput.setPrefWidth(300);

        Button connect = new Button("Login");
        connect.setPrefWidth(300);

        GridPane gp = new GridPane();
        gp.add(emailLabel, 0, 1);
        gp.add(emailInput, 1, 1);
        gp.add(pwdLabel, 0, 2);
        gp.add(pwdInput, 1, 2);
        gp.add(connect, 0,3,2,1);

        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(25);
        gp.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(gp);
        hBox.setAlignment(Pos.CENTER);


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(hBox, 500, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
