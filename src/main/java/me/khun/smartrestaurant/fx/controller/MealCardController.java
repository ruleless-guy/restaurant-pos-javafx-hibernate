package me.khun.smartrestaurant.fx.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import me.khun.smartrestaurant.application.ApplicationProperties;
import me.khun.smartrestaurant.application.ApplicationSettings;
import me.khun.smartrestaurant.application.SmartRestaurantApplication;
import me.khun.smartrestaurant.dto.custom.MealDto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringJoiner;

public class MealCardController implements Initializable {

    @FXML
    private Label categoryLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label unitPriceLabel;

    private MealDto mealDto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(this::setupItem);
    }

    private void setupItem() {
        nameLabel.setText(mealDto.getName());
        categoryLabel.setText(mealDto.getCategory().getName());
        StringJoiner sj = new StringJoiner(" ");
        unitPriceLabel.setText(sj.add(mealDto.getUnitPrice().toString()).add(ApplicationSettings.getCurrencySign().name()).toString());

        Image image = getImageOrDefaultIfNotExists(mealDto.getImagePath());
        imageView.setImage(image);
    }

    private Image getImageOrDefaultIfNotExists(String path) {
        Image image;
        try {
            image = new Image(new FileInputStream(path));
        } catch (NullPointerException | FileNotFoundException e) {
            image = new Image(getClass().getResourceAsStream(ApplicationProperties.getDefaultMealImageResourcePath()));

        }
        return image;
    }



    public void setMeal(MealDto mealDto) {
        this.mealDto = mealDto;
    }
}
