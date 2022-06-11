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
    public static Label createLabel(String name, String color) {
        Label new_label = new Label(name);
        Font font = new Font(15);
        new_label.setFont(font);
        new_label.setStyle("-fx-text-fill: " + ColorUtil.getContrastedColor(color) + ";");
        return new_label;
    }

    public static Button createIconButton(Integer width, Integer height, String icon) throws URISyntaxException {
        Button icon_btn = new Button();
        icon_btn.setStyle("-fx-cursor: hand; -fx-background-color: transparent; -fx-border-color: transparent;");
        ImageView btn_icon = ImageService.getInstance().getImage(icon);
        btn_icon.setFitWidth(width);
        btn_icon.setFitHeight(height);
        icon_btn.setGraphic(btn_icon);
        return icon_btn;
    }

    public static HBox createLabelBox(String color) {
        HBox new_box = new HBox();
        new_box.setAlignment(Pos.CENTER);
        new_box.setPadding(new Insets(5,30,5,20)); //top right bottom left
        new_box.setStyle("-fx-background-color: " + color + "; -fx-border-color: #000000;");
        return  new_box;
    }

    public static HBox createMemberBox() {
        HBox new_box = new HBox();
        new_box.setAlignment(Pos.CENTER);
        new_box.setPadding(new Insets(10,30,10,20)); //top right bottom left
        new_box.setStyle("-fx-border-color: #000000;");
        return  new_box;
    }

    public static Button createBanishedButton(boolean isBanished) {
        Button ban_btn = new Button(isBanished ? "débannir" : "bannir");
        ban_btn.setPrefWidth(100);
        ban_btn.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        return ban_btn;
    }

    public static Label createBanishedLabel(String email, boolean isBanished) {
        Label new_label = new Label(email);
        if (isBanished) {
            new_label.setText(email + " (banni du projet)");
            new_label.setStyle("-fx-font-size: 14; -fx-text-fill: #FF0000");
        } else {
            new_label.setText(email);
            new_label.setStyle("-fx-font-size: 14; -fx-text-fill: #000000");
        }

        return new_label;
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
