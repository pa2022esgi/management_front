package main.controllers;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
    @FXML private HBox box_action;
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

                box_projects.getChildren().clear();
                box_finished.getChildren().clear();
                box_ongoing.getChildren().clear();
                box_todo.getChildren().clear();
                box_action.getChildren().clear();

                if (jsArray.length() == 0) {
                    label_title.setText("AUCUN PROJET");
                    text_description.setText("");
                } else {
                    createProjectBtn();
                }

            }
        } catch (IOException | ParseException e) {
            ScreenService.getInstance().changeScreen("menu");
        }
    }

    public void createProjectBtn() throws ParseException {
        String selected_class = "-fx-background-color: #0250F7; -fx-border-color: #000000; -fx-border-radius: 5; -fx-text-fill: #FFFFFF; -fx-cursor: hand;";
        String unselect_class = "-fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-border-radius: 5; -fx-text-fill: #000000; -fx-cursor: hand;";

        for (int i = 0; i < jsArray.length(); i++) {
            Project project = new Project(jsArray.getJSONObject(i));

            Boolean with_icon = AuthService.getInstance().getUser().getId().equals(project.getAuthor());
            Button new_btn = ComponentsUtil.createProjectButton(project.getName(), with_icon);
            new_btn.setId(String.valueOf(project.getId()));

            projectMap.put(String.valueOf(project.getId()), project);

            new_btn.setOnAction(e -> {
                box_finished.getChildren().clear();
                box_ongoing.getChildren().clear();
                box_todo.getChildren().clear();
                box_action.getChildren().clear();

                currentProject = projectMap.get(new_btn.getId());
                ProjectService.getInstance().setProject(currentProject);
                label_title.setText(currentProject.getToken() + " - " + currentProject.getName());
                text_description.setText(currentProject.getDescription().length() == 0 ? "Aucune description" : currentProject.getDescription());

                createActionBtn();
                createTasks();

                if (new_btn != selectedButton) {
                    if (selectedButton != null) {
                        selectedButton.setStyle(unselect_class);
                    }
                    new_btn.setStyle(selected_class);
                    selectedButton = new_btn;
                }
            });
            box_projects.getChildren().add(new_btn);
        }

        if (ProjectService.getInstance().getProject() == null) {
            currentProject = projectMap.get(projectMap.keySet().stream().findFirst().get());
        } else {
            currentProject =  projectMap.get(String.valueOf(ProjectService.getInstance().getProject().getId()));
        }

        Button selected = (Button) scroll_projects.getScene().lookup("#" + currentProject.getId().toString());
        selectedButton = selected;
        selected.setStyle(selected_class);

        label_title.setText(currentProject.getToken() + " - " + currentProject.getName());
        text_description.setText(currentProject.getDescription().length() == 0 ? "Aucune description" : currentProject.getDescription());
        createTasks();
        createActionBtn();
    }

    public void createActionBtn () {
        try {
            Button add_btn = ComponentsUtil.createIconButton(30, 30, "icon_add_card");
            add_btn.setOnAction(ev -> {
                try {
                    addTask();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            box_action.getChildren().add(add_btn);
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        }

        if (AuthService.getInstance().getUser().getId().equals(currentProject.getAuthor())) {
            try {
                Button edit_btn = ComponentsUtil.createIconButton(27, 25, "icon_edit");
                edit_btn.setOnAction(ev -> {
                    try {
                        ProjectService.getInstance().setProject(currentProject);
                        ScreenService.getInstance().changeScreen("edit_project");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });

                box_action.getChildren().add(edit_btn);
            } catch (URISyntaxException uriSyntaxException) {
                uriSyntaxException.printStackTrace();
            }

            try {
                Button del_btn = ComponentsUtil.createIconButton(23, 25, "icon_del");
                del_btn.setOnAction(ev -> {
                    try {
                        deleteProject();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });

                box_action.getChildren().add(del_btn);
            } catch (URISyntaxException uriSyntaxException) {
                uriSyntaxException.printStackTrace();
            }
        }
    }

    public void addTask() throws IOException {
        ProjectService.getInstance().setProject(currentProject);
        TaskService.getInstance().setTask(null);
        ScreenService.getInstance().changeScreen("handle_task");
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
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(v.getTitle());
                        alert.setHeaderText(null);
                        alert.setContentText(v.getDescription());

                        alert.showAndWait();
                    });

                    bottom_box.getChildren().add(info_btn);
                }

                edit_btn.setOnAction(e -> {
                    TaskService.getInstance().setTask(v);
                    try {
                        ScreenService.getInstance().changeScreen("handle_task");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });

                del_btn.setOnAction(e -> {
                    try {
                        deleteTask(v.getId());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
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

    public void deleteTask(Integer id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Dotenv dotenv = Dotenv.load();

        Request request = new Request.Builder()
                .url(dotenv.get("BASE_URL") + "/projects/" + currentProject.getId().toString() + "/cards/" + id.toString())
                .delete()
                .addHeader("Authorization", "Bearer " + AuthService.getInstance().getUser().getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 401) {
                ScreenService.getInstance().changeScreen("login");
            } else {
                getProjects();
            }
        } catch (IOException e) {
            ScreenService.getInstance().changeScreen("menu");
        }
    }

    public void deleteProject() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Dotenv dotenv = Dotenv.load();

        Request request = new Request.Builder()
                .url(dotenv.get("BASE_URL") + "/projects/" + currentProject.getId().toString())
                .delete()
                .addHeader("Authorization", "Bearer " + AuthService.getInstance().getUser().getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 401) {
                ScreenService.getInstance().changeScreen("login");
            } else {
                ProjectService.getInstance().setProject(null);
                getProjects();
            }
        } catch (IOException e) {
            ScreenService.getInstance().changeScreen("menu");
        }
    }
}
