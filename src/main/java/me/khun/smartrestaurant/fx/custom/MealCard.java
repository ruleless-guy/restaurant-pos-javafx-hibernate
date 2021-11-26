package me.khun.smartrestaurant.fx.custom;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import me.khun.smartrestaurant.dto.custom.MealDto;
import me.khun.smartrestaurant.entity.Meal;
import me.khun.smartrestaurant.fx.controller.MealCardController;

import java.io.IOException;

public class MealCard extends VBox {

    private final MealDto mealDto;

    public MealCard(MealDto mealDto) {
        this.mealDto = mealDto;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/meal_card.fxml"));
            AnchorPane view = loader.load();
            MealCardController controller = loader.getController();
            controller.setMeal(this.mealDto);
            setPrefSize(view.getWidth(), view.getHeight());
            getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setOnMouseClicked(EventHandler<MouseEvent> e, int clickCount) {
        setOnMouseClicked(event -> {
            if(event.getClickCount() == clickCount)
                e.handle(event);
        });
    }
}
