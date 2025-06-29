package sv.linda.tasks.constructors.Task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TasksTest {

    @Test
    void testTasks() {
        Tasks tasks = new Tasks();
        assertAll(
                () -> assertTrue(tasks.getTaskList().isEmpty()),
                () -> assertNotNull(tasks.getTaskList())
        );
    }
}
