package sv.linda.tasks.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Task.TaskDAO;
import sv.linda.tasks.constructors.Task.Tasks;
import sv.linda.tasks.database.DataBaseFunctions;
import sv.linda.tasks.functions.Converter;
import sv.linda.tasks.validation.CreateLoginValidator;
import sv.linda.tasks.validation.LoginValidator;
import sv.linda.tasks.validation.TaskValidator;

import java.io.FileInputStream;
import java.util.Properties;

@Configuration
public class AppConfig implements WebMvcConfigurer, Constants {
    private Converter convert;

    @Autowired
    public void init(Gson gson) {
        this.convert = new Converter(gson);
    }

    @Bean
    public TaskValidator taskValidator() {
        return new TaskValidator(convert.getTasksNames(database.getAllData(TASKS)));
    }

    @Bean
    public LoginValidator loginValidator() {
        return new LoginValidator(convert.getLogins(database.getAllData(USERS)));
    }

    @Bean
    public CreateLoginValidator createLoginValidator() {
        return new CreateLoginValidator(convert.getLogins(database.getAllData(USERS)));
    }

    @Bean
    public Converter converter(Gson gson) {
        return new Converter(gson);
    }

    @Bean
    public TaskDAO taskDAO() {
        return new TaskDAO(convert, new Tasks());
    }

    @Bean
    public Properties properties() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\application.properties"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties file: " + e.getMessage());
        }
        return prop;
    }

    @Bean
    public ViewPages viewPages(Properties properties) {
        return new ViewPages(properties);
    }

    @Bean
    public DataBaseFunctions database(Properties properties) {
        return new DataBaseFunctions(properties.getProperty("db.url"), properties.getProperty("db.database.name"));
    }
}