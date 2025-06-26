package sv.linda.tasks.controllers;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Login.LoginDAO;
import sv.linda.tasks.constructors.Login.Logins;
import sv.linda.tasks.constructors.Task.TaskDAO;
import sv.linda.tasks.constructors.Task.Tasks;
import sv.linda.tasks.database.DataBaseFunctions;
import sv.linda.tasks.functions.Converter;
import sv.linda.tasks.functions.Save;
import sv.linda.tasks.validation.CreateLoginValidator;
import sv.linda.tasks.validation.LoginValidator;
import sv.linda.tasks.validation.TaskValidator;

import java.io.FileInputStream;
import java.util.Properties;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    public void init(Gson gson) {
        Converter convert = new Converter(gson);
    }

    @Bean
    public TaskValidator taskValidator(TaskDAO taskDAO) {
        return new TaskValidator(taskDAO);
    }

    @Bean
    public LoginValidator loginValidator(LoginDAO loginDAO) {
        return new LoginValidator(loginDAO);
    }

    @Bean
    public CreateLoginValidator createLoginValidator(LoginDAO loginDAO) {
        return new CreateLoginValidator(loginDAO);
    }

    @Bean
    public TaskDAO taskDAO(Converter convert, Tasks tasks, DataBaseFunctions database) {
        return new TaskDAO(convert, tasks, database);
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
    public ViewPages viewPages() {
        return new ViewPages(properties());
    }

    @Bean
    public DataBaseFunctions database() {
        return new DataBaseFunctions(properties().getProperty("db.url"), properties().getProperty("db.database.name"));
    }

    @Bean
    public Save save(DataBaseFunctions database, Gson gson) {
        return new Save(database, gson);
    }

    @Bean
    public Logins logins() {
        return new Logins();
    }
}