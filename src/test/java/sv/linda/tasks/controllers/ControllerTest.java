package sv.linda.tasks.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import sv.linda.tasks.constructors.Login.LoginDAO;
import sv.linda.tasks.constructors.Task.Task;
import sv.linda.tasks.constructors.Task.TaskDAO;
import sv.linda.tasks.constructors.Task.Tasks;
import sv.linda.tasks.database.DataBaseFunctions;
import sv.linda.tasks.functions.Save;
import sv.linda.tasks.validation.CreateLoginValidator;
import sv.linda.tasks.validation.LoginValidator;
import sv.linda.tasks.validation.TaskValidator;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerTest {
    @Mock
    TaskDAO taskDAO;
    @Mock
    LoginDAO loginDAO;
    @Mock
    LoginValidator loginValidator;
    @Mock
    CreateLoginValidator createLoginValidator;
    @Mock
    ViewPages viewPages;
    @Mock
    Save save;
    @Mock
    DataBaseFunctions database;

    @InjectMocks
    TaskController controller;

    @Test
    void GetTasksTest() {
        Tasks tasks = new Tasks();
        when(taskDAO.getTasks()).thenReturn(tasks);
        Tasks temp = controller.getTasks();
        Assertions.assertEquals(tasks,temp);
    }

    //TODO Figure out how to make tests when the return is ModelAndView so that tests for the rest of the controller can be made
}