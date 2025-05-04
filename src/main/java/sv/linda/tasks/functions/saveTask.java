package sv.linda.tasks.functions;

import net.minidev.json.JSONObject;
import sv.linda.tasks.constructors.Task;
import sv.linda.tasks.enums.Status;
import sv.linda.tasks.enums.TaskFields;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class saveTask {

    private JSONObject makeObject(Task task) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        JSONObject jsonObject = new JSONObject();
        for (Enum field : TaskFields.values()) {
            jsonObject.put(field.name(), task.getClass().getMethod("get" + field).invoke(task));
        }
        return jsonObject;
    }

    public void save(Task task) throws IOException {
        try {
            File temp = new File("src/main/java/sv/linda/tasks/savedTasks/" + task.getTitle() + ".json");
            if (temp.isFile() || temp.createNewFile()) {
                FileWriter file = new FileWriter("src/main/java/sv/linda/tasks/savedTasks/" + task.getTitle() + ".json");
                BufferedWriter write = new BufferedWriter(file);
                write.write(makeObject(task).toJSONString());
                write.close();
            } else {
                throw new IOException();
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        saveTask save = new saveTask();
        Task task = new Task("Task5", "new file");
        task.changeStatus(Status.toEnum("To Do"));
        save.save(task);
    }
}