package main.utils;

import javafx.scene.paint.Color;

public class ColorUtil {
    public static String toRGBCode(Color color)
    {
        return String.format("#%02X%02X%02X", (int)(color.getRed() * 255), (int)(color.getGreen() * 255), (int)(color.getBlue() * 255));
    }

    public static String getContrastedColor (String color){

        if (color.charAt(0) == '#') {
            color = color.substring(1);
        }

        int r = (int) color.charAt(0) + (int) color.charAt(1);
        int g = (int) color.charAt(2) + (int) color.charAt(2);
        int b = (int) color.charAt(3) + (int) color.charAt(4);

        int yiq = ((r * 299) + (g * 587) + (b * 114)) / 1000;

        return (yiq >= 128) ? "black" : "white";
    }
}
