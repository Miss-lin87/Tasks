package sv.linda.tasks.controllers;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sv.linda.tasks.constructors.TaskDAO;
import sv.linda.tasks.validation.TaskValidator;

@TestConfiguration
public class TestConfig {
    @Bean
    public TaskDAO taskDAO() {
        return Mockito.mock(TaskDAO.class);
    }
    @Bean
    public TaskValidator taskValidator() {
        return Mockito.mock(TaskValidator.class);
    }
}
