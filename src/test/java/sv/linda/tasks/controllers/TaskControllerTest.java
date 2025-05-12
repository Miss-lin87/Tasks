package sv.linda.tasks.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import sv.linda.tasks.constructors.Task;
import sv.linda.tasks.constructors.TaskDAO;
import sv.linda.tasks.constructors.Tasks;
import sv.linda.tasks.validation.TaskValidator;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Import(TestConfig.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mvc;
    @Mock
    private TaskDAO taskDAO;
    @Mock
    private TaskValidator taskValidator;

    @InjectMocks
    private TaskController controller;


    @Test
    void getAllTest() throws Exception {
        when(taskDAO.getTasks()).thenReturn(new Tasks());
        when(taskDAO.getTasks().getTaskList()).thenReturn(List.of(new Task("Test1", "this is just a test"), new Task("Test2", "this is just a test")));

        mvc.perform(get("/all"))
                .andExpect(status().isOk());
    }
}
