package sv.linda.tasks.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import sv.linda.tasks.constructors.Task.Task;

import java.util.List;
import java.util.Objects;

class TaskValidatorTest {
    private TaskValidator valid;
    private Task task;
    private Errors errors;

    @BeforeEach
    void setUp() {
        List<String> nameList = List.of("Test1", "Test2");
        valid = new TaskValidator(nameList);
        task = new Task();
        errors = new BeanPropertyBindingResult(task, "task");
    }

    @Test
    void emptyNameTest() {
        task.setTitle("");
        task.setDescription("This is just for a test");
        valid.validate(task, errors);
        Assertions.assertAll(
                () -> Assertions.assertTrue(errors.hasFieldErrors("title")),
                () -> Assertions.assertEquals("You need to enter a name", Objects.requireNonNull(errors.getFieldError("title")).getDefaultMessage()),
                () -> Assertions.assertFalse(errors.hasFieldErrors("description"))
        );
    }

    @Test
    void emptyDescriptionTest() {
        task.setTitle("Test3");
        valid.validate(task, errors);
        Assertions.assertAll(
                () -> Assertions.assertTrue(errors.hasFieldErrors("description")),
                () -> Assertions.assertEquals("You need a description", Objects.requireNonNull(errors.getFieldError("description")).getDefaultMessage()),
                () -> Assertions.assertFalse(errors.hasFieldErrors("title"))
        );
    }

    @Test
    void fullEmptyTest() {
        valid.validate(task, errors);
        Assertions.assertAll(
                () -> Assertions.assertTrue(errors.hasFieldErrors("title")),
                () -> Assertions.assertTrue(errors.hasFieldErrors("description")),
                () -> Assertions.assertFalse(errors.getAllErrors().isEmpty())
        );
    }

    @Test
    void shortDescriptionTest() {
        task.setTitle("Test2");
        task.setDescription("test");
        valid.validate(task, errors);
        Assertions.assertAll(
                () -> Assertions.assertTrue(errors.hasFieldErrors("description")),
                () -> Assertions.assertEquals("Description is too short. Minimum 10 characters", Objects.requireNonNull(errors.getFieldError("description")).getDefaultMessage()),
                () -> Assertions.assertFalse(errors.getAllErrors().isEmpty())
        );
    }

    @Test
    void nameAlreadyExistsTest() {
        task.setTitle("Test1");
        task.setDescription("This is just for a test");
        valid.validate(task, errors);
        Assertions.assertAll(
                () -> Assertions.assertTrue(errors.hasFieldErrors("title")),
                () -> Assertions.assertEquals("That task name is in use", Objects.requireNonNull(errors.getFieldError("title")).getDefaultMessage()),
                () -> Assertions.assertFalse(errors.hasFieldErrors("description"))
        );
    }
}