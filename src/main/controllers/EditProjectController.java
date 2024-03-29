package main.controllers;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.models.Project;
import main.models.ProjectLabel;
import main.models.User;
import main.services.AuthService;
import main.services.ProjectService;
import main.services.ScreenService;
import main.utils.ColorUtil;
import main.utils.ComponentsUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class EditProjectController {
    @FXML private Button btn_menu;
    @FXML private Button btn_label;
    @FXML private Button btn_save;
    @FXML private ColorPicker color_label;
    @FXML private Label label_error;
    @FXML private ScrollPane scroll_labels;
    @FXML private ScrollPane scroll_members;
    @FXML private TextArea text_description;
    @FXML private TextField text_label;
    @FXML private TextField text_name;
    @FXML private VBox box_labels;
    @FXML private VBox box_members;

    Project current;
    private final HashMap<String, ProjectLabel> labelMap = new HashMap<>();
    private final HashMap<String, User> usersMap = new HashMap<>();

    @FXML
    public void initialize() {
        Platform.runLater(() -> ScreenService.getInstance().setCurrent(btn_menu.getScene()));
        scroll_labels.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll_members.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        current = ProjectService.getInstance().getProject();
        text_name.setText(current.getName());
        text_description.setText(current.getDescription());
        loadLabels();
        loadMembers();
    }

    public void backToProjects () throws IOException {
        ScreenService.getInstance().changeScreen("show_projects");
    }

    public void loadLabels()  {
        current.getLabelsMap().forEach((k, v) -> {
            try {
                createLabel(v.getName(), v.getColor(), k.toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    public void loadMembers() {
        current.getUsersMap().forEach((k, v) -> {
            if (!v.getId().equals(AuthService.getInstance().getUser().getId())) {
                createMember(v.getEmail(), v.isBanished(), v.getId().toString());
                usersMap.put(v.getId().toString(), v);
            }
        });
    }

    public void addLabel () throws URISyntaxException {
        String name = text_label.getText();

        if (name.length() != 0) {
            String color = ColorUtil.toRGBCode(color_label.getValue());
            createLabel(name, color, null);
        } else {
            label_error.setText("Un nom de label est requis");
        }
    }

    public void createLabel(String name, String color, String id) throws URISyntaxException {
        Label new_label = ComponentsUtil.createLabel(name, color);
        HBox new_box = ComponentsUtil.createLabelBox(color);
        Button del_btn = ComponentsUtil.createIconButton(16, 20, "icon_del");
        if (id == null) {
            id = String.valueOf(new Date().getTime());
            labelMap.put(id, new ProjectLabel(name, color, null));
        } else {
            labelMap.put(id, new ProjectLabel(name, color, Integer.valueOf(id)));
        }
        del_btn.setId(id);
        del_btn.setOnAction(e -> {
            labelMap.remove(del_btn.getId());
            box_labels.getChildren().remove(new_box);
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        new_box.getChildren().addAll(new_label, spacer, del_btn);
        box_labels.getChildren().add(new_box);
    }

    public void createMember (String email, boolean isBanished, String id) {
        Label new_label = ComponentsUtil.createBanishedLabel(email, isBanished);
        HBox new_box = ComponentsUtil.createMemberBox();

        Button ban_btn = ComponentsUtil.createBanishedButton(isBanished);
        ban_btn.setId(id);
        ban_btn.setOnAction(e -> {
            User user = usersMap.get(ban_btn.getId());
            user.setBanished(!user.isBanished());
            if (user.isBanished()) {
                new_label.setStyle("-fx-font-size: 14; -fx-text-fill: #FF0000");
                new_label.setText(email + " (banni du projet)");
                ban_btn.setText("débannir");
            } else {
                new_label.setStyle("-fx-font-size: 14; -fx-text-fill: #000000");
                new_label.setText(email);
                ban_btn.setText("bannir");
            }
        });
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        new_box.getChildren().addAll(new_label, spacer, ban_btn);
        box_members.getChildren().add(new_box);
    }

    public void saveProject() {
        OkHttpClient client = new OkHttpClient();
        Dotenv dotenv = Dotenv.load();

        FormBody.Builder builder = new FormBody.Builder()
                .add("name", text_name.getText())
                .add("description", text_description.getText());

        AtomicInteger ind = new AtomicInteger();
        labelMap.forEach((k, v) -> {
            if (v.getId() != null) {
                builder.add("labels[" + ind + "][id]", v.getId().toString());
            }
            builder.add("labels[" + ind + "][name]", v.getName());
            builder.add("labels[" + ind + "][color]", v.getColor());
            ind.getAndIncrement();
        });

        ind.set(0);
        usersMap.forEach((k, v) -> {
            builder.add("members[" + ind + "][id]", v.getId().toString());
            builder.add("members[" + ind + "][banished]", String.valueOf(v.isBanished()));
            ind.getAndIncrement();
        });

        Request request = new Request.Builder()
                .url(dotenv.get("BASE_URL") + "/projects/" + current.getId())
                .put(builder.build())
                .addHeader("Authorization", "Bearer " + AuthService.getInstance().getUser().getToken())
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
                    ScreenService.getInstance().changeScreen("show_projects");
                }
            }
        } catch (IOException e) {
            label_error.setText("Une erreur est survenue");
        }
    }
}
