package sv.linda.tasks.savedTasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sv.linda.tasks.Constant;
import sv.linda.tasks.functions.GetInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class GetInfoTest {
    Path tempPath = Files.createTempDirectory("mockFiles");
    File tempFile1 = tempPath.resolve("test1.json").toFile();
    File tempFile2 = tempPath.resolve("test2.json").toFile();

    GetInfoTest() throws IOException {
    }

    @BeforeEach
    void setUp() throws IOException {
        tempFile1.createNewFile();
        tempFile2.createNewFile();
        tempFile1.deleteOnExit();
        tempFile2.deleteOnExit();
    }

    @Test
    void getTasksTest() {
        GetInfo info = new GetInfo() {
            {
                listOfFiles = new File[]{tempFile1,tempFile2};
            }
        };
        Assertions.assertFalse(info.getTasks().isEmpty());
        Assertions.assertTrue(info.getTasks().contains("test1"));
        Assertions.assertTrue(info.getTasks().contains("test2"));
    }

    //TODO remake somehow
    @Disabled
    @Test
    void toTaskTest() throws FileNotFoundException {
        GetInfo info = new GetInfo() {
            {
                listOfFiles = new File[]{tempFile1,tempFile2};
                con = new Constant() {
                    @Override
                    public String getSAVE_PATH() {
                        return tempPath.toAbsolutePath() + "\\%s.json";
                    }
                };
            }
        };
        System.out.println(info.toTask("test1"));
    }
}
