package main.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.services.ImageService;

import java.net.URISyntaxException;

public class ComponentsUtil {
    public static Label createLabel(String name, Color color) {
        Label new_label = new Label(name);
        String convertedColor = ColorUtil.toRGBCode(color);
        Font font = new Font(15);
        new_label.setFont(font);
        new_label.setStyle("-fx-text-fill: " + ColorUtil.getContrastedColor(convertedColor) + ";");
        return new_label;
    }

    public static Button createIconButton(Integer size, String icon) throws URISyntaxException {
        Button icon_btn = new Button();
        icon_btn.setStyle("-fx-cursor: hand;");
        ImageView btn_icon = ImageService.getInstance().getImage(icon);
        btn_icon.setFitWidth(size);
        btn_icon.setFitHeight(size);
        icon_btn.setGraphic(btn_icon);
        return icon_btn;
    }

    public static HBox createLabelBox(Color color) {
        HBox new_box = new HBox();
        new_box.setAlignment(Pos.CENTER);
        new_box.setPadding(new Insets(5,30,5,20)); //top right bottom left
        new_box.setStyle("-fx-background-color: " + ColorUtil.toRGBCode(color) + "; -fx-border-color: #000000;");
        return  new_box;
    }

    public static Button createProjectButton(String name) {
        Button new_btn = new Button();
        new_btn.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-border-radius: 5; -fx-cursor: hand;");
        new_btn.setText(name);
        Font font = new Font(14);
        new_btn.setFont(font);
        new_btn.setPrefWidth(210);
        return new_btn;
    }
}
