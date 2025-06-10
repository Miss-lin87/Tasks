package sv.linda.tasks.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.database.DataBaseFunctions;
import sv.linda.tasks.functions.Converter;
import sv.linda.tasks.validation.LoginValidator;
import sv.linda.tasks.validation.TaskValidator;

import java.io.File;
import java.util.List;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    private final DataBaseFunctions databse = Constants.Database();
    private final Converter convert = new Converter();

    @Bean
    public TaskValidator taskValidator() {
        return new TaskValidator(convert.getTasksNames(databse.getAllData("SavedTasks")));
    }

    @Bean
    public LoginValidator loginValidator() {
        return new LoginValidator(List.of(new Login("admin", "admin"), new Login("user", "user")));
    }
}