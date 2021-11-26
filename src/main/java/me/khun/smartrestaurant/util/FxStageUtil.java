package me.khun.smartrestaurant.util;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxStageUtil {

    public static Stage stageFromEvent(Event event) {
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        return (Stage) scene.getWindow();
    }
}
