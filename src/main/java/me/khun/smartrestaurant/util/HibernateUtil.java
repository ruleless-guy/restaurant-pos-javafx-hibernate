package me.khun.smartrestaurant.util;

import me.khun.smartrestaurant.application.ApplicationProperties;
import me.khun.smartrestaurant.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static volatile SessionFactory sessionFactory;

    private static void buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("config/hibernate.cfg.xml");

        /*configuration.setProperty("connection.driver_class", ApplicationProperties.getDatabaseConnectionDriver());
        configuration.setProperty("connection.url", ApplicationProperties.getDatabaseConnectionUrl());
        configuration.setProperty("connection.username", ApplicationProperties.getDatabaseConnectionUsername());
        configuration.setProperty("connection.password", ApplicationProperties.getDatabaseConnectionPassword());
        configuration.setProperty("dialect", "org.hibernate.dialect.MySQL55Dialect");
        configuration.setProperty("show_sql", "true");
        configuration.setProperty("hbm2ddl.auto", "update");*/


        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Meal.class);
        configuration.addAnnotatedClass(MealCategory.class);
        configuration.addAnnotatedClass(RestaurantTable.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory() {
        if (null == sessionFactory) {
            synchronized (HibernateUtil.class) {
                if (null == sessionFactory)
                    buildSessionFactory();
            }
        }
        return sessionFactory;
    }

}
