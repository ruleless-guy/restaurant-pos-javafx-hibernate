package me.khun.smartrestaurant.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationStrings {

    private static final Properties properties = new Properties();

    static {
        try {
            InputStream inputStream = ApplicationStrings.class.getClassLoader().getResourceAsStream("property/application-strings.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }
}
