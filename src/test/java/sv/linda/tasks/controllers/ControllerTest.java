package sv.linda.tasks.controllers;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.constructors.Login.LoginDAO;
import sv.linda.tasks.constructors.Task.Task;
import sv.linda.tasks.constructors.Task.TaskDAO;
import sv.linda.tasks.constructors.Task.Tasks;
import sv.linda.tasks.functions.Converter;
import sv.linda.tasks.validation.CreateLoginValidator;
import sv.linda.tasks.validation.LoginValidator;
import sv.linda.tasks.validation.TaskValidator;
import java.util.List;
import java.util.Properties;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


class ControllerTest {
    private final TaskDAO taskDAO;
    private final LoginDAO loginDAO;
    private final TaskController controller;
    private final MockMvc mvc;
    private TaskValidator valid = new TaskValidator(List.of("Test1", "Test2"));
    private LoginValidator loginValid = new LoginValidator(List.of(new Login(), new Login()));
    private CreateLoginValidator createloginvalid = new CreateLoginValidator(List.of(new Login(), new Login()));
    private final ViewPages viewPages = new ViewPages(Mockito.mock(Properties.class));

    ControllerTest() {
        this.taskDAO = new TaskDAO(new Converter(new Gson()), new Tasks());
        this.loginDAO = new LoginDAO();
        this.controller = new TaskController(taskDAO, loginDAO, valid, loginValid, createloginvalid, viewPages);
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