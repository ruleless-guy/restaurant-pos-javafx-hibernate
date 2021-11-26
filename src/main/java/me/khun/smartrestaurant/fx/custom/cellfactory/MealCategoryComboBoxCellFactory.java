package me.khun.smartrestaurant.fx.custom.cellfactory;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import me.khun.smartrestaurant.dto.custom.MealCategoryDto;

public class MealCategoryComboBoxCellFactory implements Callback<ListView<MealCategoryDto>, ListCell<MealCategoryDto>> {
    @Override
    public ListCell<MealCategoryDto> call(ListView<MealCategoryDto> param) {
        param.setMaxSize(180, param.getPrefHeight());
        param.setPrefHeight(100);
        return new ListCell<>(){
            @Override
            protected void updateItem(MealCategoryDto item, boolean empty) {
                super.updateItem(item, empty);
                if (null == item || empty) {
                    setText("");
                }else {
                    setText(item.getName());
                }
            }
        };
    }
}
