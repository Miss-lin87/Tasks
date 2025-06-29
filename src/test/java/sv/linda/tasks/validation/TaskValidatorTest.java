package sv.linda.tasks.validation;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import sv.linda.tasks.constructors.Task.Task;
import sv.linda.tasks.constructors.Task.TaskDAO;
import sv.linda.tasks.constructors.Task.Tasks;
import sv.linda.tasks.database.DataBaseFunctions;
import sv.linda.tasks.functions.Converter;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TaskValidatorTest {
    @Mock
    Converter converter;
    @Mock
    DataBaseFunctions databse;
    private Tasks tasks = new Tasks();
    private TaskDAO taskDAO = new TaskDAO(converter,tasks,databse);
    private TaskValidator valid;
    private Task task;
    private Errors errors;
    private final List<Task> nameList = List.of(new Task("Test1", "This is a test task"),
            new Task("Test2", "this is a test task"));

    @BeforeEach
    void setUp() {
        taskDAO.getTasks().setTaskList(nameList);
        valid = new TaskValidator(taskDAO);
        task = new Task();
        errors = new BeanPropertyBindingResult(task, "task");
    }

    @Test
    void emptyNameTest() {
        task.setTitle("");
        task.setDescription("This is just for a test");
        valid.validate(task, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors("title")),
                () -> assertEquals("You need to enter a name", Objects.requireNonNull(errors.getFieldError("title")).getDefaultMessage()),
                () -> assertFalse(errors.hasFieldErrors("description"))
        );
    }

    @Test
    void emptyDescriptionTest() {
        task.setTitle("Test3");
        task.setDescription("");
        valid.validate(task, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors("description")),
                () -> assertEquals("You need a description", Objects.requireNonNull(errors.getFieldError("description")).getDefaultMessage()),
                () -> assertFalse(errors.hasFieldErrors("title"))
        );
    }

    @Test
    void fullEmptyTest() {
        valid.validate(task, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors("title")),
                () -> assertTrue(errors.hasFieldErrors("description")),
                () -> assertFalse(errors.getAllErrors().isEmpty())
        );
    }

    @Test
    void shortDescriptionTest() {
        task.setTitle("Test2");
        task.setDescription("test");
        valid.validate(task, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors("description")),
                () -> assertEquals("Description is too short. Minimum 10 characters", Objects.requireNonNull(errors.getFieldError("description")).getDefaultMessage()),
                () -> assertFalse(errors.getAllErrors().isEmpty())
        );
    }

    @Test
    void nameAlreadyExistsTest() {
        task.setTitle("Test1");
        task.setDescription("This is just for a test");
        valid.validate(task, errors);
        assertAll(
                () -> assertTrue(errors.hasFieldErrors("title")),
                () -> assertEquals("That task name is in use", Objects.requireNonNull(errors.getFieldError("title")).getDefaultMessage()),
                () -> assertFalse(errors.hasFieldErrors("description"))
        );
    }
}