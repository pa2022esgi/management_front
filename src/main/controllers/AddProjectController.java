package main.controllers;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.models.ProjectLabel;
import main.services.ScreenService;
import main.services.AuthService;
import main.utils.ColorUtil;
import main.utils.ComponentsUtil;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AddProjectController {
    @FXML private Button btn_menu;
    @FXML private Button btn_label;
    @FXML private Button btn_join;
    @FXML private Button btn_create;
    @FXML private ColorPicker color_label;
    @FXML private Label label_error;
    @FXML private Label label_join_error;
    @FXML private ScrollPane scroll_labels;
    @FXML private TextArea text_description;
    @FXML private TextField text_join;
    @FXML private TextField text_label;
    @FXML private TextField text_name;
    @FXML private VBox box_labels;

    private final HashMap<String, ProjectLabel> labelMap = new HashMap<>();

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

            Button del_btn = ComponentsUtil.createIconButton(16, 20, "icon_del");
            String id = String.valueOf(new Date().getTime());
            del_btn.setId(id);
            labelMap.put(id, new ProjectLabel(name, ColorUtil.toRGBCode(color_label.getValue())));
            del_btn.setOnAction(e -> {
                labelMap.remove(del_btn.getId());
                box_labels.getChildren().remove(new_box);
            });

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            new_box.getChildren().addAll(new_label, spacer, del_btn);
            box_labels.getChildren().add(new_box);
        } else {
            label_error.setText("Un nom de label est requis");
        }
    }

    public void createProject() {
        OkHttpClient client = new OkHttpClient();
        Dotenv dotenv = Dotenv.load();

        FormBody.Builder builder = new FormBody.Builder()
                .add("name", text_name.getText())
                .add("description", text_description.getText());

        AtomicInteger ind = new AtomicInteger();
        labelMap.forEach((k, v) -> {
            builder.add("labels[" + ind + "][name]", v.getName());
            builder.add("labels[" + ind + "][color]", v.getColor());
            ind.getAndIncrement();
        });

        Request request = new Request.Builder()
                .url(dotenv.get("BASE_URL") + "/projects")
                .post(builder.build())
                .addHeader("Authorization", "Bearer " + AuthService.getInstance().getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 500) {
                label_error.setText("Une erreur est survenue");
            } else {
                if (response.code() == 401) {
                    ScreenService.getInstance().changeScreen("login");
                    return;
                }

                String res = response.body().string();
                JSONObject json = new JSONObject(res);

                if (response.code() == 400) {
                    if (json.has("name")) {
                        label_error.setText(json.getJSONArray("name").getString(0));
                    }
                } else {
                    System.out.println(res);
                }
            }
        } catch (IOException e) {
            label_error.setText("Une erreur est survenue");
        }
    }

    public void joinProject () {
        OkHttpClient client = new OkHttpClient();
        Dotenv dotenv = Dotenv.load();

        FormBody.Builder builder = new FormBody.Builder()
                .add("token", text_join.getText());

        Request request = new Request.Builder()
                .url(dotenv.get("BASE_URL") + "/projects/join")
                .post(builder.build())
                .addHeader("Authorization", "Bearer " + AuthService.getInstance().getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 500) {
                label_join_error.setText("Une erreur est survenue");
            } else {
                if (response.code() == 401) {
                    ScreenService.getInstance().changeScreen("login");
                    return;
                }

                String res = response.body().string();
                JSONObject json = new JSONObject(res);

                if (response.code() == 400) {
                    if (json.has("token")) {
                        label_join_error.setText(json.getJSONArray("token").getString(0));
                    } else if (json.has("error")) {
                        label_join_error.setText(json.getString("error"));
                    }
                } else {
                    System.out.println(res);
                }
            }
        } catch (IOException e) {
            label_join_error.setText("Une erreur est survenue");
        }
    }
}
