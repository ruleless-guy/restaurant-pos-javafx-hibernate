package me.khun.smartrestaurant.fx.custom;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

class SideBarButton extends HBox {
    private ImageView imageView;
    private Label label;
    private final double ICON_WIDTH = 15;
    private final double ICON_HEIGHT = 15;
    private final double PREF_HEIGHT = 40;
    private final double SPACING = 5;

    SideBarButton() {
        setPadding(new Insets(5));
        setSpacing(SPACING);
        setPrefHeight(PREF_HEIGHT);
        setAlignment(Pos.CENTER_LEFT);
        setBackground(new Background(new BackgroundFill(Paint.valueOf("#999999"), CornerRadii.EMPTY, Insets.EMPTY)));

        imageView = new ImageView();
        imageView.setFitWidth(ICON_WIDTH);
        imageView.setFitHeight(ICON_HEIGHT);

        label = new Label();

        getChildren().addAll(imageView, label);

    }

    void setIcon(String iconResourcePath) {
        Image image = new Image(getClass().getResourceAsStream(iconResourcePath));
        imageView.setImage(image);
    }

    void setLabel(String label) {
        this.label.setText(label);
    }

    static class SideBarButtonBuilder {
        private SideBarButton sideBarButton;

        SideBarButtonBuilder() {
            sideBarButton = new SideBarButton();
        }

        SideBarButtonBuilder setIcon(String iconResourcePath) {
            sideBarButton.setIcon(iconResourcePath);
            return this;
        }

        SideBarButtonBuilder setLabel(String label) {
            sideBarButton.setLabel(label);
            return this;
        }

        SideBarButtonBuilder setOnMouseClicked(EventHandler<MouseEvent> eventEventHandler) {
            sideBarButton.setOnMouseClicked(eventEventHandler);
            return this;
        }

        SideBarButtonBuilder setId(String value) {
            sideBarButton.setId(value);
            return this;
        }

        SideBarButton build() {
            return sideBarButton;
        }
    }
}