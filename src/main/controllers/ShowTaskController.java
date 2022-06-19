package main.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import main.models.Task;
import main.services.ScreenService;
import main.services.TaskService;

import java.io.IOException;

public class ShowTaskController {
    @FXML private Button btn_close;
    @FXML private Label label_title;
    @FXML private TextArea text_description;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            ScreenService.getInstance().setCurrent(btn_close.getScene());
        });

        Task current = TaskService.getInstance().getTask();

        label_title.setText(current.getTitle());
        text_description.setText(current.getDescription());
    }

    public void close() throws IOException {
        ScreenService.getInstance().changeScreen("show_projects");
    }
}
