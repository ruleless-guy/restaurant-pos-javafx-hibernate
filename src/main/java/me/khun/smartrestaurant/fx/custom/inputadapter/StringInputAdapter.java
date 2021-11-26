package me.khun.smartrestaurant.fx.custom.inputadapter;

import javafx.scene.control.TextField;

public abstract class StringInputAdapter extends TextFieldInputAdapter<String> {

    public StringInputAdapter(TextField textField) {
        super(textField);
    }

    @Override
    public void setValue(String value) {
        setText(value);
    }

    @Override
    public String getValue() {
        return getText();
    }

}
