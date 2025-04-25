package sv.linda.tasks.functions;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import sv.linda.tasks.constructors.Task;
import sv.linda.tasks.enums.Status;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class getInfo {

    public JSONObject makeJSON(String title) {
        JSONObject temp;
        try {
            Object obj = new JSONParser().parse(new FileReader("src/main/java/sv/linda/tasks/savedTasks/" + title + ".json"));
            temp = (JSONObject) obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return temp;
    }

    public List<String> getTasks() {
        ArrayList<String> tasks = new ArrayList<>();
        File folder = new File("src/main/java/sv/linda/tasks/savedTasks");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile() && !file.getName().startsWith("bin")) {
                tasks.add(file.getName().substring(0, file.getName().indexOf(".json")));
            }
        }
        return tasks;
    }

    public Task toTask(String name) {
        JSONObject tempObject = makeJSON(name);
        Task task = new Task();
        task.setTitle(tempObject.getAsString("TITLE")); // Assuming the JSON has a field "TITLE" Poop face
        task.setDescription(tempObject.getAsString("DESCRIPTION")); //Its the ass of asses
        task.setStatus(Enum.valueOf(Status.class, tempObject.getAsString("STATUS"))); // Wtf... only big ofc
        return task;
    }
}