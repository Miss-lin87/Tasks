package sv.linda.tasks.functions;

import net.minidev.json.JSONObject;
import sv.linda.tasks.Constant;
import sv.linda.tasks.constructors.Task;
import sv.linda.tasks.enums.TaskFields;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class saveTask {
    private Constant con;

    private JSONObject makeObject(Task task) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        JSONObject jsonObject = new JSONObject();
        for (Enum field : TaskFields.values()) {
            jsonObject.put(field.name(), task.getClass().getMethod("get" + field).invoke(task));
        }
        return jsonObject;
    }

    public void save(Task task) throws IOException {
        try {
            File temp = new File(con.getSAVE_PATH().formatted(task.getTitle()));
            if (temp.isFile() || temp.createNewFile()) {
                FileWriter file = new FileWriter(con.getSAVE_PATH().formatted(task.getTitle()));
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
}