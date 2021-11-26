package me.khun.smartrestaurant.entity.validator;

import me.khun.smartrestaurant.application.exception.InvalidFieldException;
import me.khun.smartrestaurant.entity.MealCategory;
import me.khun.smartrestaurant.util.StringUtil;

import static me.khun.smartrestaurant.application.ApplicationStrings.getString;

public class MealCategoryValidator {

    public static final String NAME_FIELD = "name";
    public static final String SHORT_NAME_FIELD = "short_name";
    public static final String STATUS_FIELD = "status";
    public static final String IMAGE_PATH_FIELD = "image_path";


    public static MealCategory validate(MealCategory mealCategory) throws InvalidFieldException{

        if (!MealCategory.NAME_NULLABLE && StringUtil.isNullOrBlank(mealCategory.getName()))
            throw new InvalidFieldException(getString("error.name.cannot.be.empty"), NAME_FIELD);

        if (null != mealCategory.getName() && mealCategory.getName().length() > MealCategory.MAX_NAME_LENGTH)
            throw new InvalidFieldException(getString("maximum.length.is") + " " + MealCategory.MAX_NAME_LENGTH, NAME_FIELD);

        if (!MealCategory.SHORT_NAME_NULLABLE && StringUtil.isNullOrBlank(mealCategory.getShortName()))
            throw new InvalidFieldException(getString("error.short.name.cannot.be.empty"), SHORT_NAME_FIELD);

        if (null != mealCategory.getShortName() && mealCategory.getShortName().length() > MealCategory.MAX_SHORT_NAME_LENGTH)
            throw new InvalidFieldException(getString("maximum.length.is") + " " + MealCategory.MAX_SHORT_NAME_LENGTH, SHORT_NAME_FIELD);

        if (!MealCategory.STATUS_NULLABLE && mealCategory.getStatus() == null)
            throw new InvalidFieldException(getString("error.status.cannot.be.empty"), STATUS_FIELD);

        if (!MealCategory.IMAGE_PATH_NULLABLE && StringUtil.isNullOrBlank(mealCategory.getImagePath()))
            throw new InvalidFieldException(getString("say.to.select.image"), IMAGE_PATH_FIELD);




        return mealCategory;
    }
}
