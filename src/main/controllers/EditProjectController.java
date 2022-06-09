package main.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.services.ScreenService;

import java.io.IOException;

public class EditProjectController {
    @FXML private Button btn_menu;
    @FXML private Button btn_label;
    @FXML private Button btn_save;
    @FXML private ColorPicker color_label;
    @FXML private Label label_error;
    @FXML private Label label_join_error;
    @FXML private ScrollPane scroll_labels;
    @FXML private ScrollPane scroll_members;
    @FXML private TextArea text_description;
    @FXML private TextField text_label;
    @FXML private TextField text_name;
    @FXML private VBox box_labels;
    @FXML private VBox box_members;

    @FXML
    public void initialize() {
        Platform.runLater(() -> ScreenService.getInstance().setCurrent(btn_menu.getScene()));
        scroll_labels.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll_members.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void backToMenu () throws IOException {
        ScreenService.getInstance().changeScreen("menu");
    }
}
