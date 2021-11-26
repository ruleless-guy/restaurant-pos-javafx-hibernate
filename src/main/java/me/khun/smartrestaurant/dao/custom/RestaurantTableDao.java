package me.khun.smartrestaurant.dao.custom;

import me.khun.smartrestaurant.dao.Dao;
import me.khun.smartrestaurant.entity.RestaurantTable;

import java.util.List;

public interface RestaurantTableDao extends Dao<RestaurantTable, Long> {

    List<RestaurantTable> findByKeyword(String keyword);
}
