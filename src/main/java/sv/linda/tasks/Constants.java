package sv.linda.tasks;

import sv.linda.tasks.database.DataBaseFunctions;

import java.io.FileInputStream;
import java.util.Properties;

public interface Constants {

    private static void loadProperties() {
        try {
            prop.load(new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\application.properties"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties file: " + e.getMessage());
        }
    }

    private static String getDatabaseURL() {
        loadProperties();
        return prop.getProperty("db.url");
    }

    private static String getDatabaseName() {
        loadProperties();
        return prop.getProperty("db.database.name");
    }

    private static String getDatabaseTask() {
        loadProperties();
        return prop.getProperty("db.database.tasks");
    }

    private static String getDatabaseUsers() {
        loadProperties();
        return prop.getProperty("db.database.users");
    }

    private static String getBaseError() {
        loadProperties();
        return prop.getProperty("error.base");
    }

    Properties prop = new Properties();
    String BASIC_ERROR = getBaseError();
    String TASKS = getDatabaseTask();
    String USERS = getDatabaseUsers();
    DataBaseFunctions database = new DataBaseFunctions(getDatabaseURL(), getDatabaseName());
}
