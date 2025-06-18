package sv.linda.tasks.controllers;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileInputStream;
import java.util.Properties;

public interface ViewPages {
    Properties prop = new Properties();

    private static void loadProperties() {
        try {
            prop.load(new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\application.properties"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties file: " + e.getMessage());
        }
    }

    private static String getLogin() {
        loadProperties();
        return prop.getProperty("page.login");
    }
    private static String getTasks() {
        loadProperties();
        return prop.getProperty("page.tasks");
    }
    private static String getAddLogin() {
        loadProperties();
        return prop.getProperty("page.login.add");
    }
    private static String getAddTask(){
        loadProperties();
        return prop.getProperty("page.tasks.add");
    }
    private static String getError(){
        loadProperties();
        return prop.getProperty("page.error");
    }

    ModelAndView NEWLOGIN = new ModelAndView(getAddLogin());
    ModelAndView NEWTASK = new ModelAndView(getAddTask());
    ModelAndView LOGIN = new ModelAndView(getLogin());
    ModelAndView MAIN = new ModelAndView(getTasks());
    ModelAndView REDIRECT_MAIN = new ModelAndView("redirect:/main");
    ModelAndView ERROR = new ModelAndView(getError());
}
