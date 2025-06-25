package sv.linda.tasks;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.google.gson.Gson;
import sv.linda.tasks.database.DataBaseFunctions;

import java.io.FileInputStream;
import java.util.Properties;

public interface Constants {

    private static Properties loadProperties() {
        Properties temp = new Properties();
        try {
            temp.load(new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\application.properties"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties file: " + e.getMessage());
        }
        return temp;
    }

    private static String getDatabaseURL() {
        return prop.getProperty("db.url");
    }

    private static String getDatabaseName() {
        return prop.getProperty("db.database.name");
    }

    private static String getDatabaseTask() {
        return prop.getProperty("db.database.tasks");
    }

    private static String getDatabaseUsers() {
        return prop.getProperty("db.database.users");
    }

    private static String getBaseError() {
        return prop.getProperty("error.base");
    }

    Properties prop = loadProperties();
    String BASIC_ERROR = getBaseError();
    String TASKS = getDatabaseTask();
    String USERS = getDatabaseUsers();
    DataBaseFunctions database = new DataBaseFunctions(getDatabaseURL(), getDatabaseName());
    Gson gson = new Gson();
    String TITLE = "title";
    String DESCRIPTION = "description";
    String USERNAME = "username";
    String PASSWORD = "password";
}
