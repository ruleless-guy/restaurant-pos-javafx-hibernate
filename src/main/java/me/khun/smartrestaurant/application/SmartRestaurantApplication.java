package me.khun.smartrestaurant.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.khun.smartrestaurant.util.FxViewUtil;

import java.io.IOException;

public class SmartRestaurantApplication extends Application {

    private static Stage primaryStage;


    @Override
    public void start(Stage primaryStage) {
        SmartRestaurantApplication.primaryStage = primaryStage;
        showLoginWindow();
    }

    public static void start(String[] args) {
        launch(args);
    }

    public static void showLoginWindow() {

        try {
            FXMLLoader loader = new FXMLLoader(SmartRestaurantApplication.class.getResource("/view/login_window.fxml"));
            Parent view = loader.load();
            Scene scene = new Scene(view);
            primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMainWindow() {
        Parent view = FxViewUtil.loadView("/view/main_window.fxml");
        Stage stage = new Stage();
        stage.setScene(new Scene(view, 1200, 800));
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
        primaryStage = stage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
