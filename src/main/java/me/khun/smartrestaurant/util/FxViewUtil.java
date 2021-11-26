package me.khun.smartrestaurant.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.FileChooser;
import me.khun.smartrestaurant.application.ApplicationProperties;

import java.io.File;
import java.io.IOException;

public class FxViewUtil {

    public static Parent loadView(String resourcePath) {

        Parent view = null;
        try {

            FXMLLoader loader = new FXMLLoader(FxViewUtil.class.getResource(resourcePath));
            view = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

}
