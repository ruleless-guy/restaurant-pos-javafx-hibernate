package me.khun.smartrestaurant.fx.custom;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;

public class FloatingMessageView extends Popup {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 60;

//    private static final int MARGIN = 20;

    private static final long DURATION = 5000;


    public FloatingMessageView(String message) {


        Label label = new Label(message);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setMinSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        label.setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        label.setPrefSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        label.setStyle("-fx-opacity:0.7; -fx-border-width:2px; -fx-border-color:gray; -fx-background-color:black;-fx-text-fill:white;");

        setAutoFix(true);
        setAutoHide(false);
        setHideOnEscape(false);
        setWidth(DEFAULT_WIDTH);
        setHeight(DEFAULT_HEIGHT);
        getContent().add(label);

    }

    @Override
    public void show(Window owner) {
        show(owner, Pos.BOTTOM_RIGHT);
    }


    public void show(Window owner, Pos pos) {

        ChangeListener<Number> listener = (l, o, n)-> setPosition(owner, pos);

        owner.widthProperty().addListener(listener);
        owner.heightProperty().addListener(listener);
        owner.xProperty().addListener(listener);
        owner.yProperty().addListener(listener);

        setOnShown(e->{
            setPosition(owner, pos);

            new Timeline(new KeyFrame(
                    Duration.millis(DURATION),
                    ae -> hide())).play();
        });


        super.show(owner);
    }

    private void setPosition(Window owner, Pos pos) {
        Point2D p = getPoint2D(owner, pos);
        setX(p.getX());
        setY(p.getY());
    }

    private Point2D getPoint2D(Window owner, Pos pos) {

        double x = 0,y = 0;

        switch (pos) {
            case CENTER -> {
                x = owner.getX() + owner.getWidth() / 2 - getWidth() / 2;
                y = owner.getY() + owner.getHeight() / 2 - getHeight() / 2;
            }
            case TOP_LEFT -> {
                x = owner.getX(); // + MARGIN;
                y = owner.getY(); //+ MARGIN;
            }
            case TOP_RIGHT -> {
                x = owner.getX() + owner.getWidth() - getWidth(); //- MARGIN;
                y = owner.getY(); //+ MARGIN;
            }
            case BOTTOM_RIGHT -> {
                x = owner.getX() + owner.getWidth() - getWidth(); // - MARGIN;
                y = owner.getY() + owner.getHeight() - getHeight(); // - MARGIN;
            }

            case BOTTOM_LEFT -> {
                x = owner.getX(); // + MARGIN;
                y = owner.getY() + owner.getHeight() - getHeight(); // - MARGIN;
            }
        }

        return new Point2D(x, y);
    }
}
