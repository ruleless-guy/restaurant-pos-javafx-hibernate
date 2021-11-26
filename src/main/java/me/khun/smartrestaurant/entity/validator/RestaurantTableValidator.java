package me.khun.smartrestaurant.entity.validator;

import me.khun.smartrestaurant.application.exception.InvalidFieldException;
import me.khun.smartrestaurant.entity.RestaurantTable;
import me.khun.smartrestaurant.util.StringUtil;

import static me.khun.smartrestaurant.application.ApplicationStrings.getString;

public class RestaurantTableValidator {

    public static final String NAME_FIELD = "name";
    public static final String SEAT_COUNT_FIELD = "seat_count";
    public static final String STATUS_FIELD = "status";
    public static final String IMAGE_PATH_FIELD = "image_path";

    public static RestaurantTable validate(RestaurantTable entity) {

        if (!RestaurantTable.NAME_NULLABLE && StringUtil.isNullOrBlank(entity.getName()))
            throw new InvalidFieldException(getString("error.name.cannot.be.empty"), NAME_FIELD);

        if (null != entity.getName() && entity.getName().length() > RestaurantTable.MAX_NAME_LENGTH)
            throw new InvalidFieldException(getString("maximum.length.is") + " " + RestaurantTable.MAX_NAME_LENGTH, NAME_FIELD);

        if (!RestaurantTable.SEAT_COUNT_NULLABLE && null == entity.getSeatCount())
            throw new InvalidFieldException(getString("error.seat.count.cannot.be.empty"), SEAT_COUNT_FIELD);

        if (!RestaurantTable.STATUS_NULLABLE && null == entity.getStatus())
            throw new InvalidFieldException(getString("error.status.cannot.be.empty"), STATUS_FIELD);

        if (!RestaurantTable.IMAGE_PATH_NULLABLE && null == entity.getImagePath())
            throw new InvalidFieldException(getString("say.to.select.image"), IMAGE_PATH_FIELD);

        return entity;
    }
}
