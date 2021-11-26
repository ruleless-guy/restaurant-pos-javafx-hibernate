package me.khun.smartrestaurant.fx.custom.inputadapter;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import me.khun.smartrestaurant.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleLoginIdInputAdapter extends StringInputAdapter {

    public SimpleLoginIdInputAdapter(TextField textField) {
        super(textField);
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String regex = "[A-Za-z0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(newValue);
        if (!matcher.matches() && !StringUtil.isNullOrBlank(newValue))
            Platform.runLater(()->{
                setText(oldValue);
                getTextField().positionCaret(StringUtil.isNullOrEmpty(oldValue) ? 0 : oldValue.length());
            });
    }
}
