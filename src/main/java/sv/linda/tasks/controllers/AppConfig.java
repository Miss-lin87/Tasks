package sv.linda.tasks.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sv.linda.tasks.Constants;
import sv.linda.tasks.functions.Converter;
import sv.linda.tasks.validation.CreateLoginValidator;
import sv.linda.tasks.validation.LoginValidator;
import sv.linda.tasks.validation.TaskValidator;

@Configuration
public class AppConfig implements WebMvcConfigurer, Constants {
    private final Converter convert = new Converter();

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
}