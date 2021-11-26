package me.khun.smartrestaurant.fx.custom.inputadapter;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import me.khun.smartrestaurant.util.StringUtil;

public class IntegerInputAdapter extends NumberInputAdapter{

    public IntegerInputAdapter(TextField textField) {
        super(textField);
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!StringUtil.isNullOrEmpty(newValue)) {
            try {
                Integer.parseInt(newValue);
            }catch (NumberFormatException | NullPointerException e) {
                Platform.runLater(()->{
                    setText(StringUtil.normalizeNotNullTrim(oldValue));
                    getTextField().positionCaret(StringUtil.isNullOrEmpty(oldValue) ? 0 : oldValue.length());
                });
            }
        }
    }
}
