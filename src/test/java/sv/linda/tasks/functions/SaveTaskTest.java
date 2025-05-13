package sv.linda.tasks.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import sv.linda.tasks.constructors.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class SaveTaskTest {
    Path tempPath = Files.createTempDirectory("testDir");
    SaveTask save;

    SaveTaskTest() throws IOException {
        save = new SaveTask(tempPath.toString() + "/%s.json");
        tempPath.toFile().deleteOnExit();
    }

    @Test
    void saveTest() throws IOException {
        Task task = new Task("test", "This is a test task");
        save.save(task);
        Assertions.assertAll(
                () -> Assertions.assertTrue(Files.exists(Path.of(tempPath.toString().formatted(task.getTitle()))))
        );
    }
}
