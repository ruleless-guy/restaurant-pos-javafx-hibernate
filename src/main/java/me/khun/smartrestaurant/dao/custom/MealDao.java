package me.khun.smartrestaurant.dao.custom;

import me.khun.smartrestaurant.dao.Dao;
import me.khun.smartrestaurant.entity.Meal;

import java.util.List;

public interface MealDao extends Dao<Meal, Long> {

    List<Meal> findByKeyWord(String keyword);
}
