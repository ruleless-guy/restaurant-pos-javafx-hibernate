package me.khun.smartrestaurant.application;

import java.io.*;
import java.util.Properties;

public class ApplicationProperties {

    private static final Properties properties = new Properties();
    private static final File propertyFile = new File("src/main/resources/property/application.properties");

    static {
        try {
            InputStream inputStream = ApplicationStrings.class.getClassLoader().getResourceAsStream("property/application.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDatabaseConnectionDriver() {
        return properties.getProperty("connection.driver.class");
    }

    public static String getDatabaseConnectionUrl() {
        return properties.getProperty("connection.url");
    }

    public static String getDatabaseConnectionUsername() {
        return properties.getProperty("connection.username");
    }

    public static String getDatabaseConnectionPassword() {
        return properties.getProperty("connection.password");
    }

    public static String getDefaultNoImageResourcePath() {
        return properties.getProperty("default.no.image.resource.path");
    }

    public static String getDefaultMealImageResourcePath() {
        return properties.getProperty("default.meal.image.resource.path");
    }

    public static String getDefaultMealCategoryImageResourcePath() {
        return properties.getProperty("default.meal.category.image.resource.path");
    }

    public static String getDefaultTableImageResourcePath() {
        return properties.getProperty("default.table.image.resource.path");
    }

    public static String getLastVisitedImagePath() {
        return properties.getProperty("last.visited.image.path");
    }

    public static void setLastVisitedImagePath(String path) {

        properties.setProperty("last.visited.image.path", path);

        try (OutputStream s = new FileOutputStream(propertyFile)) {
            properties.store(s, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
