package me.khun.smartrestaurant.bo.custom;

import me.khun.smartrestaurant.bo.Bo;
import me.khun.smartrestaurant.dto.custom.MealCategoryDto;

import java.util.List;

public interface MealCategoryBo extends Bo<MealCategoryDto, Long> {

    List<MealCategoryDto> findByKeyword(String keyword);
}
