package me.khun.smartrestaurant.bo.custom.impl;

import me.khun.smartrestaurant.bo.Bo;
import me.khun.smartrestaurant.bo.BoType;

public class BoFactory {

    private static BoFactory instance;

    private BoFactory() {}

    public static BoFactory getInstance() {
        if (null == instance) {
            synchronized (BoFactory.class) {
                if (null == instance) {
                    instance = new BoFactory();
                }
            }
        }
        return instance;
    }

    public Bo<?, ?> getBo(BoType type) {
        return switch (type) {
            case USER -> new UserBoImpl();
            case MEAL -> new MealBoImpl();
            case MEAL_CATEGORY -> new MealCategoryBoImpl();
            case RESTAURANT_TABLE -> new RestaurantTableBoImpl();
        };
    }
}
