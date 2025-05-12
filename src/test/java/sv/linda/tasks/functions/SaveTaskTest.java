package sv.linda.tasks.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sv.linda.tasks.constructors.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveTaskTest extends SaveTask {
    Path tempPath = Files.createTempDirectory("testDir");

    //TODO make it delete stuff
    public SaveTaskTest() throws IOException {
        this.TASKS_PATH = tempPath.toString();
        this.SAVE_PATH = tempPath + "\\%s.json";
    }

    @Test
    void saveTest() throws IOException {
        Task task = new Task("test", "This is a test task");
        save(task);
        Assertions.assertAll(
                () -> Assertions.assertTrue(Files.exists(Path.of(SAVE_PATH.formatted(task.getTitle())))),
                () -> Assertions.assertFalse(TASKS_PATH.isEmpty())
        );
    }
}
