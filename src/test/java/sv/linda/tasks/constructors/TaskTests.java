package sv.linda.tasks.constructors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sv.linda.tasks.enums.Status;

class TaskTests {

    @Test
    void makeTaskTest() {
        Task tempTask = new Task();
        Assertions.assertAll(
                () -> Assertions.assertEquals("", tempTask.getTitle()),
                () -> Assertions.assertEquals("", tempTask.getDescription()),
                () -> Assertions.assertEquals(Status.TODO, tempTask.getStatus()),
                () -> Assertions.assertTrue(tempTask.getSubtasks().isEmpty()),
                () -> Assertions.assertNotNull(tempTask)
        );
    }

    @Test
    void makeFullTaskTest() {
        Task tempTask = new Task("Test1", "This is a test");
        Assertions.assertAll(
                () -> Assertions.assertEquals("Test1", tempTask.getTitle()),
                () -> Assertions.assertEquals("This is a test", tempTask.getDescription()),
                () -> Assertions.assertEquals(Status.TODO, tempTask.getStatus()),
                () -> Assertions.assertTrue(tempTask.getSubtasks().isEmpty()),
                () -> Assertions.assertNotNull(tempTask)
        );
    }

    @Test
    void changeTitleTest() {
        Task tempTask = new Task("Test2", "This is a test");
        tempTask.setTitle("Test3");
        Assertions.assertAll(
                () -> Assertions.assertEquals("Test3", tempTask.getTitle()),
                () -> Assertions.assertNotEquals("Test2", tempTask.getTitle())
        );
    }

    @Test
    void changeDescriptionTest() {
        Task tempTask = new Task("Test1", "This is a test");
        tempTask.setDescription("This is not a test");
        Assertions.assertAll(
                () -> Assertions.assertEquals("This is not a test", tempTask.getDescription()),
                () -> Assertions.assertNotEquals("This is a test", tempTask.getDescription())
        );
    }

    @Test
    void changeStatusTest() {
        Task tempTask = new Task("Test1", "This is a test");
        tempTask.setStatus(Status.DONE);
        Assertions.assertAll(
                () -> Assertions.assertEquals(Status.DONE, tempTask.getStatus()),
                () -> Assertions.assertNotEquals(Status.TODO, tempTask.getStatus())
        );
    }
}