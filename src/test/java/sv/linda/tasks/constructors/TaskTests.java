package sv.linda.tasks.constructors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sv.linda.tasks.enums.Status;

class TaskTests {

    @Test
    void testTaskCreation() {
        Task task = new Task();
        // Verify that the task is created with default values
        Assertions.assertAll(
            () -> Assertions.assertEquals("", task.getTitle()),
            () -> Assertions.assertEquals("", task.getDescription()),
            () -> Assertions.assertEquals(Status.TODO, task.getStatus()),
            () -> Assertions.assertTrue(task.getSubtasks().isEmpty())
        );
        // Verify that the task is created with specified values
        Task taskWithValues = new Task("Test1", "This is a test");
        Assertions.assertAll(
                () -> Assertions.assertEquals("Test1", taskWithValues.getTitle()),
                () -> Assertions.assertEquals("This is a test", taskWithValues.getDescription()),
                () -> Assertions.assertEquals(Status.TODO, taskWithValues.getStatus()),
                () -> Assertions.assertTrue(taskWithValues.getSubtasks().isEmpty())
        );
    }

    @Test
    void testChangeTask() {
        Task task = new Task("Test2", "This is a test");
        // Test change name
        task.setTitle("Test3");
        Assertions.assertEquals("Test3", task.getTitle());
        Assertions.assertNotEquals("Test2", task.getTitle());
        // Test change description
        task.setDescription("This is a test2");
        Assertions.assertEquals("This is a test2", task.getDescription());
        Assertions.assertNotEquals("This is a test", task.getDescription());
        // Test change status
        task.setStatus(Status.DONE);
        Assertions.assertEquals(Status.DONE, task.getStatus());
        Assertions.assertNotEquals(Status.TODO, task.getStatus());
    }
}
