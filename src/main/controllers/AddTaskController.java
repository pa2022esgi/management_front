package main.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.services.ScreenService;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;

public class AddTaskController {
    @FXML private Button btn_create;
    @FXML private Button btn_cancel;
    @FXML private ComboBox select_status;
    @FXML private ComboBox select_member;
    @FXML private TextField text_name;
    @FXML private TextArea text_description;
    @FXML private DatePicker picker_date;
    @FXML private CheckComboBox select_labels;
    @FXML private Label label_error;

    @FXML
    public void initialize() {
        Platform.runLater(() -> ScreenService.getInstance().setCurrent(btn_create.getScene()));
    }

    public void cancel() throws IOException {
        ScreenService.getInstance().changeScreen("show_projects");
    }
}
