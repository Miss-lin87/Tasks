package sv.linda.tasks.functions;

import com.google.gson.Gson;
import sv.linda.tasks.Constant;
import sv.linda.tasks.constructors.Task;

import java.io.*;

//TODO use constants and not extend them
//TODO Inject the Path instead of using a constant
public class SaveTask extends Constant {
    protected final Gson gson;

    public SaveTask() {
        this.gson = new Gson();
    }

    public void save(Task task) throws IOException {
        try (Writer write = new FileWriter(SAVE_PATH.formatted(task.getTitle()))) {
            gson.toJson(task,write);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}