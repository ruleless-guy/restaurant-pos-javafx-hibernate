package me.khun.smartrestaurant.fx.custom.inputadapter;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;

public abstract class NumberInputAdapter extends TextFieldInputAdapter<Number>{

    public NumberInputAdapter(TextField textField) {
        super(textField);
    }

    @Override
    public void setValue(Number number) {
        var text = null == number ? "" : number.toString();
        setText(text);
    }

    @Override
    public Number getValue() {
        try {
            return Double.parseDouble(getText());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }


}
