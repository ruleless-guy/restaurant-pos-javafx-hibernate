package me.khun.smartrestaurant.entity.validator;

import me.khun.smartrestaurant.application.exception.InvalidFieldException;
import me.khun.smartrestaurant.entity.Meal;
import me.khun.smartrestaurant.util.StringUtil;

import static me.khun.smartrestaurant.application.ApplicationStrings.getString;

public class MealValidator {

    public static final String NAME_FIELD = "name";
    public static final String SHORT_NAME_FIELD = "short_name";
    public static final String UNIT_PRICE_FIELD = "unit_price";
    public static final String CATEGORY_FIELD = "category";
    public static final String STATUS_FIELD = "status";
    public static final String SIZE_FILED = "size";
    public static final String IMAGE_PATH_FIELD = "image_path";


    public static Meal validate(Meal meal) throws InvalidFieldException {

        if (!Meal.NAME_NULLABLE && StringUtil.isNullOrBlank(meal.getName()))
            throw new InvalidFieldException(getString("error.name.cannot.be.empty"), NAME_FIELD);

        if (null != meal.getName() && meal.getName().length() > Meal.MAX_NAME_LENGTH)
            throw new InvalidFieldException(getString("maximum.length.is") + " " + Meal.MAX_NAME_LENGTH, NAME_FIELD);

        if (!Meal.SHORT_NAME_NULLABLE && StringUtil.isNullOrBlank(meal.getShortName()))
            throw new InvalidFieldException(getString("error.short.name.cannot.be.empty"), SHORT_NAME_FIELD);

        if (null != meal.getShortName() && meal.getShortName().length() > Meal.MAX_SHORT_NAME_LENGTH)
            throw new InvalidFieldException(getString("maximum.length.is") + " " + Meal.MAX_SHORT_NAME_LENGTH, SHORT_NAME_FIELD);

        if (!Meal.UNIT_PRICE_NULLABLE && meal.getUnitPrice() == null)
            throw new InvalidFieldException(getString("error.unit.price.cannot.be.empty"), UNIT_PRICE_FIELD);

        if (!Meal.CATEGORY_NULLABLE && meal.getCategory() == null)
            throw new InvalidFieldException(getString("error.category.cannot.be.empty"), CATEGORY_FIELD);

        if (!Meal.STATUS_NULLABLE && meal.getStatus() == null)
            throw new InvalidFieldException(getString("error.status.cannot.be.empty"), STATUS_FIELD);

        if (!Meal.SIZE_NULLABLE && meal.getSize() == null)
            throw new InvalidFieldException(getString("error.status.cannot.be.empty"), SIZE_FILED);

        if (!Meal.IMAGE_PATH_NULLABLE && StringUtil.isNullOrBlank(meal.getImagePath()))
            throw new InvalidFieldException(getString("say.to.select.image"), IMAGE_PATH_FIELD);

        return meal;
    }
}
