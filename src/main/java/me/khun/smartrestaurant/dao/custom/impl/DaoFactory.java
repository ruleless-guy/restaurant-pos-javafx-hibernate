package me.khun.smartrestaurant.dao.custom.impl;

import me.khun.smartrestaurant.dao.Dao;
import me.khun.smartrestaurant.dao.DaoType;

public class DaoFactory {

    private static DaoFactory instance;

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        if (null == instance) {
            synchronized (DaoFactory.class) {
                if (null == instance) {
                    instance = new DaoFactory();
                }
            }
        }
        return instance;
    }

    public Dao<?, ?> getDao(DaoType type) {
        return switch (type) {
            case USER -> new UserDaoHibernateImpl();
            case MEAL -> new MealDaoHibernateImpl();
            case MEAL_CATEGORY -> new MealCategoryDaoHibernateImpl();
            case RESTAURANT_TABLE -> new RestaurantTableDaoHibernateImpl();
        };
    }
}
