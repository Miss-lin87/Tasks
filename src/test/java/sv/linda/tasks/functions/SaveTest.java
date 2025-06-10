package sv.linda.tasks.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sv.linda.tasks.constructors.Task.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class SaveTest {
    Path tempPath = Files.createTempDirectory("testDir");
    Save save;

    SaveTest() throws IOException {
        save = new Save(tempPath.toString() + "/%s.json");
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
