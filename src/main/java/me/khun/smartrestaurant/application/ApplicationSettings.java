package me.khun.smartrestaurant.application;

import me.khun.smartrestaurant.entity.CurrencySign;

import java.io.*;
import java.util.Properties;

public class ApplicationSettings {

    private static final Properties properties = new Properties();
    private static final File propertyFile = new File("src/main/resources/property/application-settings.properties");

    static {
        try {
            InputStream inputStream = ApplicationStrings.class.getClassLoader().getResourceAsStream("property/application-settings.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getDecimalPlaces() {
        return Integer.parseInt(properties.getProperty("decimal.places"));
    }

    public static void setCurrencySign(CurrencySign sign) {
        properties.setProperty("currency.sign", sign.name());
        storeProperty();
    }

    public static CurrencySign getCurrencySign() {
        return CurrencySign.valueOf(properties.getProperty("currency.sign"));
    }

    private static void storeProperty() {
        try (OutputStream s = new FileOutputStream(propertyFile)) {
            properties.store(s, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
