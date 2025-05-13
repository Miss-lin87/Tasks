package sv.linda.tasks.constructors.Task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TasksTest {
    @Test
    void testTasks() {
        Tasks tasks = new Tasks();
        Assertions.assertAll(
                () -> Assertions.assertTrue(tasks.getTaskList().isEmpty()),
                () -> Assertions.assertNotNull(tasks.getTaskList())
        );
    }
}
