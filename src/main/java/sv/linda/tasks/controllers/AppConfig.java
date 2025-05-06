package sv.linda.tasks.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sv.linda.tasks.validation.TaskValidator;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public TaskValidator taskValidator() {
        return new TaskValidator();
    }
}
