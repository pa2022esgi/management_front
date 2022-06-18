package main.controllers;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.models.KeyValuePair;
import main.models.Project;
import main.services.AuthService;
import main.services.ProjectService;
import main.services.ScreenService;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.controlsfx.control.CheckComboBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class AddTaskController {
    @FXML private Button btn_create;
    @FXML private Button btn_cancel;
    @FXML private ComboBox<KeyValuePair> select_status;
    @FXML private ComboBox<KeyValuePair> select_member;
    @FXML private TextField text_name;
    @FXML private TextArea text_description;
    @FXML private DatePicker picker_date;
    @FXML private CheckComboBox<KeyValuePair> select_labels;
    @FXML private Label label_error;

    Project current;

    @FXML
    public void initialize() throws IOException {
        Platform.runLater(() -> ScreenService.getInstance().setCurrent(btn_create.getScene()));
        current = ProjectService.getInstance().getProject();

        current.getUsersMap().forEach((k, v) -> {
            select_member.getItems().add(new KeyValuePair(v.getId(), v.getEmail()));
        });

        current.getLabelsMap().forEach((k, v) -> {
            select_labels.getItems().add(new KeyValuePair(v.getId(), v.getName()));
        });

        getStatuses();
    }

    public void cancel() throws IOException {
        ScreenService.getInstance().changeScreen("show_projects");
    }

    public void getStatuses() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Dotenv dotenv = Dotenv.load();

        Request request = new Request.Builder()
                .url(dotenv.get("BASE_URL") + "/cards/statuses")
                .get()
                .addHeader("Authorization", "Bearer " + AuthService.getInstance().getUser().getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 500) {
                ScreenService.getInstance().changeScreen("show_projects");
            } else {
                if (response.code() == 401) {
                    ScreenService.getInstance().changeScreen("login");
                    return;
                }

                String res = response.body().string();
                JSONArray array = new JSONArray(res);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    select_status.getItems().add(new KeyValuePair(obj.getInt("id"), obj.getString("name")));
                }
            }
        } catch (IOException e) {
            ScreenService.getInstance().changeScreen("show_projects");
        }
    }

    public Boolean verifyParameters() {

        if (text_name.getText().equals("")) {
            label_error.setText("Un nom est requis");
            return false;
        }

        if (picker_date.getValue() == null) {
            label_error.setText("Une date est requise");
            return false;
        }

        if (select_status.getValue() == null) {
            label_error.setText("Un status est requis");
            return false;
        }

        return true;
    }

    public void createTask() {
        OkHttpClient client = new OkHttpClient();
        Dotenv dotenv = Dotenv.load();

        if (verifyParameters()) {
            FormBody.Builder builder = new FormBody.Builder()
                    .add("title", text_name.getText())
                    .add("description", text_description.getText())
                    .add("due_date", picker_date.getValue().toString())
                    .add("status_id", select_status.getValue().getKey().toString());

            AtomicInteger ind = new AtomicInteger();
            select_labels.getCheckModel().getCheckedItems().forEach((v) -> {
                builder.add("labels[" + ind + "]", v.getKey().toString());
                ind.getAndIncrement();
            });

            if (select_member.getValue() != null) {
                builder.add("user_id", select_member.getValue().getKey().toString());
            }

            Request request = new Request.Builder()
                    .url(dotenv.get("BASE_URL") + "/projects/" + current.getId().toString() + "/cards")
                    .post(builder.build())
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

                    ScreenService.getInstance().changeScreen("show_projects");
                }
            } catch (IOException e) {
                label_error.setText("Une erreur est survenue");
            }
        }
    }
}
