package sv.linda.tasks.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import sv.linda.tasks.constructors.Task;
import java.util.Objects;

class TaskValidatorTest {
    private TaskValidator valid;
    private Task task;

    @BeforeEach
    void setUp() {
        valid = new TaskValidator() {
            @Override
            public void validate(Object task, Errors error) {
                super.nameList.clear();
                super.nameList.add("Test1");
                super.validate(task, error);
            }
        };
        task = new Task();
    }

    @Test
    void emptyNameTest() {
        task.setTitle("");
        task.setDescription("This is just for a test");
        Errors error = new BeanPropertyBindingResult(task, "task");
        valid.validate(task, error);

        Assertions.assertTrue(error.hasFieldErrors("title"));
        Assertions.assertEquals("You need to enter a name", Objects.requireNonNull(error.getFieldError("title")).getDefaultMessage());
        Assertions.assertFalse(error.hasFieldErrors("description"));
    }

    @Test
    void emptyDescriptionTest() {
        task.setTitle("Test2");
        Errors error = new BeanPropertyBindingResult(task, "task");
        valid.validate(task, error);

        Assertions.assertTrue(error.hasFieldErrors("description"));
        Assertions.assertEquals("You need a description", Objects.requireNonNull(error.getFieldError("description")).getDefaultMessage());
        Assertions.assertFalse(error.hasFieldErrors("title"));
    }

    @Test
    void fullEmptyTest() {
        Errors error = new BeanPropertyBindingResult(task, "task");
        valid.validate(task, error);

        Assertions.assertTrue(error.hasFieldErrors("title"));
        Assertions.assertTrue(error.hasFieldErrors("description"));
    }

    @Test
    void shortDescriptionTest() {
        task.setTitle("Test2");
        task.setDescription("test");
        Errors error = new BeanPropertyBindingResult(task, "task");
        valid.validate(task, error);

        Assertions.assertTrue(error.hasFieldErrors("description"));
        Assertions.assertEquals("Description is too short. Minimum 10 characters", Objects.requireNonNull(error.getFieldError("description")).getDefaultMessage());
    }

    @Test
    void nameAlreadyExistsTest() {
        task.setTitle("Test1");
        task.setDescription("This is just for a test");
        Errors error = new BeanPropertyBindingResult(task, "task");
        valid.validate(task, error);

        Assertions.assertTrue(error.hasFieldErrors("title"));
        Assertions.assertEquals("That task name is in use", Objects.requireNonNull(error.getFieldError("title")).getDefaultMessage());
        Assertions.assertFalse(error.hasFieldErrors("description"));
    }
}