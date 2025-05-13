package sv.linda.tasks.functions;

import com.google.gson.Gson;
import sv.linda.tasks.constructors.Task.Task;
import java.io.*;

public class SaveTask {
    protected final Gson gson;
    private String SAVE_PATH;

    public SaveTask(String savePath) {
        this.gson = new Gson();
        this.SAVE_PATH = savePath;
    }

    public void save(Task task) throws IOException {
        try (Writer write = new FileWriter(SAVE_PATH.formatted(task.getTitle()))) {
            gson.toJson(task,write);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}