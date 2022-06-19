package main.controllers;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import main.models.Project;
import main.services.ProjectService;
import main.services.ScreenService;
import main.services.AuthService;
import main.services.TaskService;
import main.utils.ColorUtil;
import main.utils.ComponentsUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;

public class ShowProjectsController {
    @FXML private Button btn_menu;
    @FXML private Button btn_edit;
    @FXML private Button btn_delete;
    @FXML private Button btn_task;
    @FXML private ScrollPane scroll_projects;
    @FXML private VBox box_projects;
    @FXML private ScrollPane scroll_todo;
    @FXML private VBox box_todo;
    @FXML private ScrollPane scroll_ongoing;
    @FXML private VBox box_ongoing;
    @FXML private ScrollPane scroll_finished;
    @FXML private VBox box_finished;
    @FXML private Label label_title;
    @FXML private TextArea text_description;

    private JSONArray jsArray;
    private final HashMap<String, Project> projectMap = new HashMap<>();
    private Project currentProject;
    private Button selectedButton;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            ScreenService.getInstance().setCurrent(btn_menu.getScene());
            try {
                getProjects();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        scroll_projects.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll_todo.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll_ongoing.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll_finished.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void backToMenu () throws IOException {
        ScreenService.getInstance().changeScreen("menu");
    }

    public void getProjects () throws IOException {
        OkHttpClient client = new OkHttpClient();
        Dotenv dotenv = Dotenv.load();

        Request request = new Request.Builder()
                .url(dotenv.get("BASE_URL") + "/projects")
                .get()
                .addHeader("Authorization", "Bearer " + AuthService.getInstance().getUser().getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 500) {
                ScreenService.getInstance().changeScreen("menu");
            } else {
                if (response.code() == 401) {
                    ScreenService.getInstance().changeScreen("login");
                    return;
                }

                String res = response.body().string();
                jsArray = new JSONArray(res);

                createProjectBtn();
                createTasks();
            }
        } catch (IOException | ParseException e) {
            ScreenService.getInstance().changeScreen("menu");
        }
    }

    public void createProjectBtn() throws ParseException {
        for (int i = 0; i < jsArray.length(); i++) {
            Button new_btn = ComponentsUtil.createProjectButton(jsArray.getJSONObject(i).getString("name"));
            new_btn.setId(String.valueOf(i));
            projectMap.put(String.valueOf(i), new Project(jsArray.getJSONObject(i)));
            String selected_class = "-fx-background-color: #0250F7; -fx-border-color: #000000; -fx-border-radius: 5; -fx-text-fill: #FFFFFF; -fx-cursor: hand;";
            String unselect_class = "-fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-border-radius: 5; -fx-text-fill: #000000; -fx-cursor: hand;";
            new_btn.setOnAction(e -> {
                currentProject = projectMap.get(new_btn.getId());
                label_title.setText(currentProject.getToken() + " - " + currentProject.getName());
                text_description.setText(currentProject.getDescription().length() == 0 ? "Aucune description" : currentProject.getDescription());

                if (new_btn != selectedButton) {
                    if (selectedButton != null) {
                        selectedButton.setStyle(unselect_class);
                    }
                    new_btn.setStyle(selected_class);
                    selectedButton = new_btn;
                }
            });
            box_projects.getChildren().add(new_btn);

            if (i == 0) {
                currentProject = projectMap.get(String.valueOf(i));
                selectedButton = new_btn;
                new_btn.setStyle(selected_class);
                label_title.setText(currentProject.getToken() + " - " + currentProject.getName());
                text_description.setText(currentProject.getDescription().length() == 0 ? "Aucune description" : currentProject.getDescription());
            }
        }
    }

    public void editProject() throws IOException {
        ProjectService.getInstance().setProject(currentProject);
        ScreenService.getInstance().changeScreen("edit_project");
    }

    public void deleteProject() {

    }

    public void addTask() throws IOException {
        ProjectService.getInstance().setProject(currentProject);
        ScreenService.getInstance().changeScreen("add_task");
    }

    public void createTasks() {
        currentProject.getTasksMap().forEach((k, v) -> {
            VBox new_box = new VBox();
            new_box.setStyle("-fx-border-color: #000000");
            new_box.setPadding(new Insets(10));

            FlowPane contain_labels = new FlowPane();
            contain_labels.setHgap(5);
            contain_labels.setVgap(5);
            v.getLabelsMap().forEach((id, val) -> {
                Label label = new Label(val.getName());
                label.setPadding(new Insets(3));
                label.setStyle("-fx-background-color: " + val.getColor() + "; -fx-background-radius: 5; -fx-text-fill: " + ColorUtil.getContrastedColor(val.getColor()));
                contain_labels.getChildren().add(label);
            });

            Label title = new Label(v.getTitle());
            title.setPadding(new Insets(5, 0, 0, 0));
            title.setStyle("-fx-font-weight: bold; -fx-font-size: 14");

            new_box.getChildren().addAll(contain_labels, title);

            if (v.getUser() != null) {
                Label user = new Label("Assigné à : " + v.getUser().getEmail());
                new_box.getChildren().add(user);
            }

            HBox bottom_box = new HBox();
            bottom_box.setPadding(new Insets(20, 0,0 ,0));
            bottom_box.setAlignment(Pos.BOTTOM_LEFT);
            try {
                Label date = new Label("pour le " + v.getDate());

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                Button edit_btn = ComponentsUtil.createIconButton(20, 20, "icon_edit");
                Button del_btn = ComponentsUtil.createIconButton(16, 20, "icon_del");

                bottom_box.getChildren().addAll(date, spacer);

                if (!v.getDescription().isEmpty()){
                    Button info_btn = ComponentsUtil.createIconButton(25, 18, "icon_eye");
                    info_btn.setOnAction(e -> {
                        TaskService.getInstance().setTask(v);
                        try {
                            ScreenService.getInstance().changeScreen("show_task");
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });

                    bottom_box.getChildren().add(info_btn);
                }

                edit_btn.setOnAction(e -> {
                    TaskService.getInstance().setTask(v);

                });

                bottom_box.getChildren().addAll(edit_btn, del_btn);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            new_box.getChildren().add(bottom_box);

            if (v.getStatus() == 1) {
                box_todo.getChildren().add(new_box);
            } else if (v.getStatus() == 2) {
                box_ongoing.getChildren().add(new_box);
            } else if (v.getStatus() == 3) {
                box_finished.getChildren().add(new_box);
            }
        });
    }
}
