package me.khun.smartrestaurant.fx.custom.inputadapter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import me.khun.smartrestaurant.util.StringUtil;

public class FloatingNumberInputAdapter extends NumberInputAdapter{

    public FloatingNumberInputAdapter(TextField textField) {
        super(textField);
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

        if (!StringUtil.isNullOrEmpty(newValue)) {
            try {
                if (newValue.endsWith("d") || newValue.endsWith("D") || newValue.endsWith("f") || newValue.endsWith("F"))
                    newValue = newValue.substring(0, newValue.length() - 1);
                Double.parseDouble(newValue);
                setText(StringUtil.normalizeNotNullTrim(newValue));
            }catch (NumberFormatException | NullPointerException e) {
                Platform.runLater(()->{
                    setText(StringUtil.normalizeNotNullTrim(oldValue));
                    getTextField().positionCaret(StringUtil.isNullOrEmpty(oldValue) ? 0 : oldValue.length());
                });
            }
        }


    }
}
