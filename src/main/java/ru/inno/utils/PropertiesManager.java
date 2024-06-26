package ru.inno.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
    static Properties properties = new Properties();
    private static void readPropertiesFile() {
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream("src/main/resources/config.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }
    public static String getPropertyValue (String propertyName) {
        if (properties.isEmpty()) {
            readPropertiesFile();
        }
        return properties.getProperty(propertyName);
    }

    public static String getUrl(UrnName urnName){
        String urn = switch (urnName) {
            case LOGIN -> getPropertyValue("login.page");
            case BOOKS -> getPropertyValue("books.page");
            case PROFILE -> getPropertyValue("profile.page");
            case API_BOOKS -> getPropertyValue("api.books");
        };

        return getPropertyValue("application.url") + urn;
    }
}
