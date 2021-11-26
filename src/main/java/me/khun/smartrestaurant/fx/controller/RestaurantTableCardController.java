package me.khun.smartrestaurant.fx.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import me.khun.smartrestaurant.application.ApplicationProperties;
import me.khun.smartrestaurant.dto.custom.RestaurantTableDto;
import me.khun.smartrestaurant.util.StringUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class RestaurantTableCardController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Label seatCountLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private ImageView tableImageVIew;

    private RestaurantTableDto table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this::setupItem);
    }

    public void setRestaurantTable(RestaurantTableDto table) {
        this.table = table;
    }

    private void setupItem() {
        nameLabel.setText(StringUtil.normalizeNotNullTrim(table.getName()));
        seatCountLabel.setText(StringUtil.normalizeNotNullTrim(String.valueOf(table.getSeatCount())));
        statusLabel.setText(StringUtil.normalizeNotNullTrim(table.getStatus().name()));
        Image image = getImageOrDefaultIfNotExists(table.getImagePath());
        tableImageVIew.setImage(image);
    }

    private Image getImageOrDefaultIfNotExists(String path) {
        Image image;
        try {
            image = new Image(new FileInputStream(path));
        } catch (NullPointerException | FileNotFoundException e) {
            image = new Image(getClass().getResourceAsStream(ApplicationProperties.getDefaultTableImageResourcePath()));

        }
        return image;
    }

}
