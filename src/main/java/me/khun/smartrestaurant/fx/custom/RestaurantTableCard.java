package me.khun.smartrestaurant.fx.custom;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import me.khun.smartrestaurant.dto.custom.RestaurantTableDto;
import me.khun.smartrestaurant.fx.controller.RestaurantTableCardController;

import java.io.IOException;

public class RestaurantTableCard extends VBox {

    private final RestaurantTableDto table;

    public RestaurantTableCard(RestaurantTableDto table) {
        this.table = table;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/restaurant_table_card.fxml"));
            AnchorPane view = loader.load();
            RestaurantTableCardController controller = loader.getController();
            controller.setRestaurantTable(this.table);
            setPrefSize(view.getPrefWidth(), view.getPrefHeight());
            getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOnMouseClicked(EventHandler<MouseEvent> e, int clickCount) {
        setOnMouseClicked(event -> {
            if (event.getClickCount() == clickCount)
                e.handle(event);
        });
    }
}
