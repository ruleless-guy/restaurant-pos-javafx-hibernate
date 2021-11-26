package me.khun.smartrestaurant.bo.custom;

import me.khun.smartrestaurant.bo.Bo;
import me.khun.smartrestaurant.dto.custom.MealDto;

import java.util.List;

public interface MealBo extends Bo<MealDto, Long> {

    List<MealDto> findByKeyword(String keyword);
}
