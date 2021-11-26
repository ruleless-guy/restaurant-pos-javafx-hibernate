package me.khun.smartrestaurant.fx.custom;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import me.khun.smartrestaurant.fx.controller.MainWindowController;
import me.khun.smartrestaurant.auth.LoginManager;

import java.io.IOException;

public class MainWindow extends Stage {

    private static MainWindow instance;
    private BorderPane rootPane;
    private StackPane stackPane;
    private VBox sideBarButtonContainer;
    private MainWindowController controller;

    private MainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_window.fxml"));
            Parent view = loader.load();
            controller = loader.getController();
            setScene(new Scene(view));
            setResizable(true);
            centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MainWindow getInstance() {
        if (null == instance) {
            synchronized (MainWindow.class) {
                if (null == instance) {
                    instance = new MainWindow();
                }
            }
        }
        return instance;
    }


}
