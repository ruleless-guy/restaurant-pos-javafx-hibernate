package me.khun.smartrestaurant.dao.custom;

import me.khun.smartrestaurant.dao.Dao;
import me.khun.smartrestaurant.dto.custom.MealCategoryDto;
import me.khun.smartrestaurant.entity.MealCategory;

import java.util.List;

public interface MealCategoryDao extends Dao<MealCategory, Long> {

    List<MealCategory> findByKeyWord(String keyword);
}
