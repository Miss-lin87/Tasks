package sv.linda.tasks.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;
import sv.linda.tasks.constructors.Task;
import sv.linda.tasks.constructors.TaskDAO;
import sv.linda.tasks.validation.TaskValidator;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class ControllerTest {
    private final TaskDAO taskDAO;
    private final TaskController controller;
    private final MockMvc mvc;
    private TaskValidator valid = new TaskValidator(List.of("Test1", "Test2"));

    ControllerTest() {
        this.taskDAO = new TaskDAO();
        this.controller = new TaskController(taskDAO, valid);
        this.mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setValidator(valid)
                .build();
    }

    @BeforeEach
    void setUp() {
        taskDAO.getTasks().getTaskList().clear();
        taskDAO.getTasks().getTaskList().add(new Task("Test1", "This is a test"));
    }

    @Test
    void getAllTest() throws Exception {
        mvc.perform(get("/all"))
                .andExpect(status().isOk());
    }

    @Test
    void getNameTest() throws Exception {
        mvc.perform(get("/Test1"))
                .andExpect(status().isOk());
    }

    //TODO Figure out how to make tests when the return is ModelAndView so that tests for the rest of the controller can be made
}