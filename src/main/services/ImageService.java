package main.services;

import javafx.scene.image.ImageView;

import java.net.URISyntaxException;
import java.util.HashMap;

public class ImageService {
    private final HashMap<String, String> imageMap = new HashMap<>();
    private final static ImageService INSTANCE = new ImageService();

    private ImageService() {}

    public static ImageService getInstance() {
        return INSTANCE;
    }

    public void addImage(String name, String path) {
        imageMap.put(name, path);
    }

    public ImageView getImage(String name) throws URISyntaxException {
        return new ImageView(getClass().getResource(imageMap.get(name)).toURI().toString());
    }
}
