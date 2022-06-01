package main.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.net.URISyntaxException;

public class AddProjectController {
    @FXML private Button btn_menu;
    @FXML private Button btn_label;
    @FXML private Button btn_join;
    @FXML private Button btn_create;
    @FXML private ColorPicker color_label;
    @FXML private Label label_error;
    @FXML private ScrollPane scroll_labels;
    @FXML private TextArea text_description;
    @FXML private TextField text_join;
    @FXML private TextField text_label;
    @FXML private TextField text_name;
    @FXML private VBox box_labels;

    @FXML
    public void initialize() {
        Platform.runLater(() -> ScreenController.getInstance().setCurrent(btn_menu.getScene()));
    }

    public void backToMenu () throws IOException {
        ScreenController.getInstance().changeScreen("menu");
    }

    public void addLabel () throws URISyntaxException {
        String name = text_label.getText();

        if (name.length() != 0) {
            String color = toRGBCode(color_label.getValue());
            String style_class = "-fx-background-color : " + color + "; -fx-border-color : #000000;";
            Font font = new Font(15);
            HBox new_box = new HBox();
            Label new_label = new Label(name);

            Button del_btn = new Button();
            ImageView btn_icon = new ImageView(getClass().getResource("../ressources/icon/delete.png").toURI().toString());
            btn_icon.setFitWidth(20);
            btn_icon.setFitHeight(20);
            del_btn.setAlignment(Pos.CENTER_RIGHT);
            del_btn.setGraphic(btn_icon);

            scroll_labels.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            new_label.setFont(font);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            new_box.setPadding(new Insets(5,30,5,20)); //top right bottom left
            new_box.setAlignment(Pos.CENTER);
            new_box.setStyle(style_class);
            new_box.getChildren().addAll(new_label, spacer, del_btn);

            box_labels.getChildren().add(new_box);
        }
    }
    public static String toRGBCode(Color color)
    {
        return String.format("#%02X%02X%02X", (int)(color.getRed() * 255), (int)(color.getGreen() * 255), (int)(color.getBlue() * 255));
    }
}
