package main.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.services.PluginService;
import main.services.ScreenService;
import org.controlsfx.control.ToggleSwitch;

import java.io.IOException;

public class PluginsController {
    @FXML private Button btn_menu;
    @FXML private ScrollPane scroll_plugins;
    @FXML private VBox box_plugins;

    @FXML
    public void initialize() throws IOException {
        Platform.runLater(() -> ScreenService.getInstance().setCurrent(btn_menu.getScene()));
        if (PluginService.getInstance().pluginInstance == null) {
            PluginService.getInstance().loadPlugins();
        }
        scroll_plugins.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        loadPlugin();
    }

    public void backToMenu() throws IOException {
        ScreenService.getInstance().changeScreen("menu");
    }

    public void loadPlugin() {
        if (PluginService.getInstance().pluginInstance != null) {
            HBox box = new HBox();
            box.setStyle("-fx-border-color: #000000; -fx-border-radius: 5;");
            box.setPadding(new Insets(10));

            ToggleSwitch toggle = new ToggleSwitch();
            toggle.setOnMouseClicked(e -> {
                PluginService.getInstance().activated = !PluginService.getInstance().activated;
            });

            toggle.setSelected(PluginService.getInstance().activated);

            Label name = new Label(PluginService.getInstance().name);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            box.getChildren().addAll(name, spacer, toggle);

            box_plugins.getChildren().add(box);
        }
    }
}
