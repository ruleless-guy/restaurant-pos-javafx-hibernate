package me.khun.smartrestaurant.bo.custom;

import me.khun.smartrestaurant.bo.Bo;
import me.khun.smartrestaurant.dto.custom.RestaurantTableDto;

import java.util.List;

public interface RestaurantTableBo extends Bo<RestaurantTableDto, Long> {
    List<RestaurantTableDto> findByKeyword(String keyword);
}
