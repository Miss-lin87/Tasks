package sv.linda.tasks.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sv.linda.tasks.Constant;
import sv.linda.tasks.functions.GetInfo;
import sv.linda.tasks.validation.TaskValidator;

import java.io.File;

@Configuration
public class AppConfig extends Constant implements WebMvcConfigurer {

    @Bean
    public TaskValidator taskValidator() {
        return new TaskValidator(new GetInfo(new File(TASKS_PATH).listFiles()).getTasks());
    }
}
