package sv.linda.tasks.constructors.Task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.linda.tasks.enums.Status;

import static org.junit.jupiter.api.Assertions.*;

class TaskTests {
    Task tempTask;

    @BeforeEach
    void init() {
        tempTask = new Task("Test1", "This is a test");
    }

    @Test
    void makeTaskTest() {
        tempTask = new Task();
        assertAll(
                () -> assertEquals("", tempTask.getTitle()),
                () -> assertEquals("", tempTask.getDescription()),
                () -> assertEquals(Status.TODO, tempTask.getStatus()),
                () -> assertTrue(tempTask.getSubtasks().isEmpty()),
                () -> assertNotNull(tempTask)
        );
    }

    @Test
    void makeFullTaskTest() {
        assertAll(
                () -> assertEquals("Test1", tempTask.getTitle()),
                () -> assertEquals("This is a test", tempTask.getDescription()),
                () -> assertEquals(Status.TODO, tempTask.getStatus()),
                () -> assertTrue(tempTask.getSubtasks().isEmpty()),
                () -> Assertions.assertNotNull(tempTask)
        );
    }

    @Test
    void changeTitleTest() {
        tempTask.setTitle("Test3");
        assertAll(
                () -> assertEquals("Test3", tempTask.getTitle()),
                () -> Assertions.assertNotEquals("Test2", tempTask.getTitle())
        );
    }

    @Test
    void changeDescriptionTest() {
        tempTask.setDescription("This is not a test");
        assertAll(
                () -> assertEquals("This is not a test", tempTask.getDescription()),
                () -> Assertions.assertNotEquals("This is a test", tempTask.getDescription())
        );
    }

    @Test
    void changeStatusTest() {
        tempTask.setStatus(Status.DONE);
        assertAll(
                () -> assertEquals(Status.DONE, tempTask.getStatus()),
                () -> Assertions.assertNotEquals(Status.TODO, tempTask.getStatus())
        );
    }
}