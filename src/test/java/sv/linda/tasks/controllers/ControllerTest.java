package sv.linda.tasks.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sv.linda.tasks.constructors.Task;
import sv.linda.tasks.constructors.TaskDAO;
import sv.linda.tasks.validation.TaskValidator;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class ControllerTest {
    private TaskDAO taskDAO;
    private TaskController controller;
    private MockMvc mvc;

    ControllerTest() {
        this.taskDAO = new TaskDAO();
        this.controller = new TaskController(taskDAO, new TaskValidator(List.of("Test1", "Test2")));
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
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
}