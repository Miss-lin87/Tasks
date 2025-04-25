package sv.linda.tasks.functions;

import net.minidev.json.JSONObject;
import sv.linda.tasks.constructors.Task;
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
            if (temp.createNewFile()) {
                FileWriter file = new FileWriter("src/main/java/sv/linda/tasks/savedTasks/" + task.getTitle() + ".json");
                BufferedWriter write = new BufferedWriter(file);
                write.write(makeObject(task).toJSONString());
                write.close();
            } else {
                System.out.println("This not work");
                throw new IOException();
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public static void main(String[] args) {
        Task task = new Task("Task4", "This is an example task.");
        saveTask saver = new saveTask();
        try {
            saver.save(task);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}