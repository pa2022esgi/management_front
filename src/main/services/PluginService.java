package main.services;

import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class PluginService {
    public Object pluginInstance = null;
    public String name = null;
    public Boolean activated = false;
    ArrayList<Method> methods = new ArrayList<>();

    private final static PluginService INSTANCE = new PluginService();
    private PluginService() {}
    public static PluginService getInstance() {
        return INSTANCE;
    }

    public void loadPlugins() throws IOException {
        File plugin = pluginExist("management-plugin.jar");

        if (plugin != null) {

            load(plugin);
        }
    }

    public File pluginExist(String name) {
        File file = new File("./plugins/" + name);

        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    public void load(File plugin) {
        try {
            URLClassLoader child = new URLClassLoader(
                    new URL[] {plugin.toURI().toURL()},
                    this.getClass().getClassLoader()
            );

            Class<?> classToLoad = Class.forName("management.plugin.ToPDF", true, child);
            pluginInstance = classToLoad.getDeclaredConstructor().newInstance();

            methods.add(classToLoad.getDeclaredMethod("getPluginName"));

            Class<?>[] project = new Class[1];
            project[0] = JSONObject.class;
            methods.add(classToLoad.getDeclaredMethod("toPdf", project));

            getPluginName();

        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void getPluginName() throws InvocationTargetException, IllegalAccessException {
        name = (String) methods.get(0).invoke(pluginInstance);
    }

    public void toPdf(JSONObject project) throws InvocationTargetException, IllegalAccessException, IOException {
        methods.get(1).invoke(pluginInstance, project);
        File result = new File("./pdf/result.pdf");

        Desktop.getDesktop().open(result);
    }
}
