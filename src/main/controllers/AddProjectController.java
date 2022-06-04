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
import javafx.scene.text.Font;
import main.services.ScreenService;
import main.utils.ColorUtil;
import main.utils.ComponentsUtil;

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
        Platform.runLater(() -> ScreenService.getInstance().setCurrent(btn_menu.getScene()));
        scroll_labels.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void backToMenu () throws IOException {
        ScreenService.getInstance().changeScreen("menu");
    }

    public void addLabel () throws URISyntaxException {
        String name = text_label.getText();

        if (name.length() != 0) {
            Label new_label = ComponentsUtil.createLabel(name, color_label.getValue());

            HBox new_box = ComponentsUtil.createLabelBox(color_label.getValue());

            Button del_btn = ComponentsUtil.createIconButton(20, "icon_del");
            del_btn.setOnAction(e -> box_labels.getChildren().remove(new_box));

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            new_box.getChildren().addAll(new_label, spacer, del_btn);
            box_labels.getChildren().add(new_box);
        } else {
            label_error.setText("Un nom de label est requis");
        }
    }
}
