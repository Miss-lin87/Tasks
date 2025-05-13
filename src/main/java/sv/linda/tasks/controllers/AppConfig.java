package sv.linda.tasks.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.functions.GetInfo;
import sv.linda.tasks.validation.LoginValidator;
import sv.linda.tasks.validation.TaskValidator;

import java.io.File;
import java.util.List;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    private final String TASKS_PATH = Constants.TasksPath();

    @Bean
    public TaskValidator taskValidator() {
        return new TaskValidator(new GetInfo(new File(TASKS_PATH).listFiles()).getTasks());
    }

    @Bean
    public LoginValidator loginValidator() {
        return new LoginValidator(List.of(new Login("admin", "admin"), new Login("user", "user")));
    }
}