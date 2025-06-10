package sv.linda.tasks.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sv.linda.tasks.constructors.Task.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class GetInfoTest {
    Path tempPath = Files.createTempDirectory("mockFiles");
    File tempFile1 = tempPath.resolve("test1.json").toFile();
    File tempFile2 = tempPath.resolve("test2.json").toFile();
    String content = "{\"title\":\"test1\",\"description\":\"This is a temp file for testing\",\"status\":\"TODO\",\"subtasks\":[]}";

    GetInfoTest() throws IOException {
        //super(new File[]{});
    }

    @BeforeEach
    void setUp() throws IOException {
        tempFile1.createNewFile();
        tempFile2.createNewFile();
        tempFile1.deleteOnExit();
        tempFile2.deleteOnExit();
        //listOfFiles = new File[]{tempFile1, tempFile2};
    }

    //TODO fix the tests after convertion change
    @Disabled
    @Test
    void getTasksTest() {
        Assertions.assertAll(
                //() -> Assertions.assertFalse(getTasks().isEmpty()),
                //() -> Assertions.assertTrue(getTasks().contains("test1")),
                //() -> Assertions.assertTrue(getTasks().contains("test2"))
        );
    }

    //TODO fix the tests after convertion change
    @Disabled
    @Test
    void toTaskTest() throws IOException {
        Task task = new Task("test1", "This is a temp file for testing");
        Files.write(tempFile1.toPath(), content.getBytes());
        Assertions.assertAll(
                //() -> Assertions.assertEquals(task.toString(), toTask("test1").toString()),
                //() -> Assertions.assertEquals(task.getTitle(), toTask("test1").getTitle()),
                //() -> Assertions.assertEquals(task.getDescription(), toTask("test1").getDescription()),
                //() -> Assertions.assertNotNull(toTask("test1"))
        );
    }
    //TODO fix the tests after convertion change
    @Disabled
    @Test
    void makeTaskTest() throws IOException {
        Files.write(tempFile1.toPath(), content.getBytes());
        Assertions.assertAll(
                //() -> Assertions.assertEquals(Task.class, makeTask(tempFile1).getClass()),
                //() -> Assertions.assertNotNull(makeTask(tempFile1)),
                //() -> Assertions.assertEquals("test1", makeTask(tempFile1).getTitle())
        );
    }
}