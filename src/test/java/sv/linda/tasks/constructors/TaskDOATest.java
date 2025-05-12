package sv.linda.tasks.constructors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TaskDOATest {

    @Test
    void testEmptyTaskDAO() {
        TaskDAO taskDAO = new TaskDAO();
        Assertions.assertAll(
                () -> Assertions.assertTrue(taskDAO.getTasks().getTaskList().isEmpty()),
                () -> Assertions.assertNotEquals(null, taskDAO.getTasks().getTaskList())
        );
    }

    @Test
    void testFillTaskDAO() {
        TaskDAO taskDAO = new TaskDAO();
        for (int i = 0; i < 10; i++) {
            Task tempTask = new Task("Test" + i, "This is a test");
            taskDAO.addTask(tempTask);
        }
        Assertions.assertEquals(10, taskDAO.getTasks().getTaskList().size());
    }
}
