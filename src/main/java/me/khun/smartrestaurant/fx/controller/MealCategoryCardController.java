package me.khun.smartrestaurant.fx.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import me.khun.smartrestaurant.application.ApplicationProperties;
import me.khun.smartrestaurant.application.SmartRestaurantApplication;
import me.khun.smartrestaurant.dto.custom.MealCategoryDto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MealCategoryCardController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;

    private MealCategoryDto mealCategory;

    public void setMealCategory(MealCategoryDto mealCategoryDto) {
        this.mealCategory = mealCategoryDto;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this::setupItem);
    }

    private void setupItem() {
        nameLabel.setText(mealCategory.getName());
        Image image = getImageOrDefaultIfNotExists(mealCategory.getImagePath());

        imageView.setImage(image);
    }

    private Image getImageOrDefaultIfNotExists(String path) {
        Image image;
        try {
            image = new Image(new FileInputStream(path));
        } catch (NullPointerException | FileNotFoundException e) {
            image = new Image(getClass().getResourceAsStream(ApplicationProperties.getDefaultMealCategoryImageResourcePath()));

        }
        return image;
    }
}
