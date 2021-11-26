package me.khun.smartrestaurant.fx.custom;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import me.khun.smartrestaurant.dto.custom.MealCategoryDto;
import me.khun.smartrestaurant.fx.controller.MealCategoryCardController;

import java.io.IOException;
import java.util.function.Consumer;

public class MealCategoryCard extends VBox {

    private final MealCategoryDto mealCategoryDto;

    public MealCategoryCard(MealCategoryDto mealCategoryDto) {

        this.mealCategoryDto = mealCategoryDto;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/meal_category_card.fxml"));
            VBox view = fxmlLoader.load();
            MealCategoryCardController controller = fxmlLoader.getController();
            controller.setMealCategory(this.mealCategoryDto);
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
