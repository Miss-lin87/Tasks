package sv.linda.tasks.functions;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import sv.linda.tasks.Constant;
import sv.linda.tasks.constructors.Task;
import sv.linda.tasks.enums.Status;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GetInfo {

    @Deprecated
    public JSONObject makeJSON(String title) {
        JSONObject temp;
        try {
            Object obj = new JSONParser().parse(new FileReader(Constant.SAVE_PATH.formatted(title)));
            temp = (JSONObject) obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return temp;
    }

    public List<String> getTasks() {
        ArrayList<String> tasks = new ArrayList<>();
        File folder = new File(Constant.TASKS_PATH);
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                tasks.add(file.getName().substring(0, file.getName().indexOf(".json")));
            }
        }
        return tasks;
    }

    public Task toTask(String name) {
        JSONObject tempObject = makeJSON(name);
        Task task = new Task();
        task.setTitle(tempObject.getAsString("TITLE"));
        task.setDescription(tempObject.getAsString("DESCRIPTION"));
        task.setStatus(Enum.valueOf(Status.class, tempObject.getAsString("STATUS")));
        return task;
    }
}