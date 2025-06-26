package sv.linda.tasks.constructors.Task;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.linda.tasks.database.DataBaseFunctions;
import sv.linda.tasks.functions.Converter;

import static org.junit.jupiter.api.Assertions.*;

class TaskDAOTest {
    private Converter converter = Mockito.mock(Converter.class);
    private Tasks tasks = Mockito.mock(Tasks.class);
    private DataBaseFunctions database = Mockito.mock(DataBaseFunctions.class);

    @Test
    void testEmptyTaskDAO() {
        TaskDAO taskDAO = new TaskDAO(converter, tasks, database);
        assertAll(
                () -> assertTrue(taskDAO.getTasks().getTaskList().isEmpty()),
                () -> assertNotEquals(null, taskDAO.getTasks().getTaskList())
        );
    }

    @Test
    void testFillTaskDAO() {
        TaskDAO taskDAO = new TaskDAO(converter, tasks, database);
        for (int i = 0; i < 10; i++) {
            Task tempTask = new Task("Test" + i, "This is a test");
            taskDAO.addTask(tempTask);
        }
        assertAll(
                () -> assertEquals(10, taskDAO.getTasks().getTaskList().size()),
                () -> assertTrue(taskDAO.getTasks().getTaskList().contains(new Task("Test0", "This is a test"))),
                () -> assertTrue(taskDAO.getTasks().getTaskList().contains(new Task("Test9", "This is a test"))),
                () -> assertFalse(taskDAO.getTasks().getTaskList().contains(new Task("Test100", "This is a test also")))
        );
    }
}
