package me.khun.smartrestaurant.fx.custom.inputadapter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;


public abstract class TextFieldInputAdapter<T> implements ChangeListener<String> {

    private final TextField TEXT_FIELD;

    public TextFieldInputAdapter(TextField textField) {
        this.TEXT_FIELD = textField;
        this.TEXT_FIELD.textProperty().addListener(this);
    }

    abstract public void setValue(T value);

    abstract public T getValue();

    public void clear() {
        getTextField().clear();
    }

    protected TextField getTextField() {
        return TEXT_FIELD;
    }


    protected void setText(String text) {
        TEXT_FIELD.setText(text);
    }

    protected String getText() {
        return TEXT_FIELD.getText();
    }

}
