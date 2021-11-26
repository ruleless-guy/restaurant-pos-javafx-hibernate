package me.khun.smartrestaurant.fx.custom;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.khun.smartrestaurant.application.ApplicationProperties;

import java.io.File;

public class ImageFileChooser {
    private final FileChooser fileChooser;

    public ImageFileChooser() {
        this(null);
    }

    public ImageFileChooser(String title) {
        var lastVisited = new File(ApplicationProperties.getLastVisitedImagePath());
        var home = new File(System.getProperty("user.home"));

        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(lastVisited.exists() ? lastVisited : home);
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.jpg", "*.jpeg", "*.png"));

    }

    public File showOpenDialog(Stage owner) {
        var file = fileChooser.showOpenDialog(owner);

        if (null != file)
            ApplicationProperties.setLastVisitedImagePath(file.getParent());

        return file;
    }
}
