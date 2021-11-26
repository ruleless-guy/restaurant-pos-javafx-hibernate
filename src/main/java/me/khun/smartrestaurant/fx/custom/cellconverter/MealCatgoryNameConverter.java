package me.khun.smartrestaurant.fx.custom.cellconverter;

import javafx.util.StringConverter;
import me.khun.smartrestaurant.dto.custom.MealCategoryDto;

public class MealCatgoryNameConverter extends StringConverter<MealCategoryDto> {
    @Override
    public String toString(MealCategoryDto object) {
        return null == object ? null : object.getName();
    }

    @Override
    public MealCategoryDto fromString(String string) {
        return null;
    }
}
