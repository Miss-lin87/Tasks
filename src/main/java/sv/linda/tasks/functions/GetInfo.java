package sv.linda.tasks.functions;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import sv.linda.tasks.Constant;
import sv.linda.tasks.constructors.Task;
import sv.linda.tasks.enums.Status;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GetInfo {
    public Constant con = new Constant();
    protected File[] listOfFiles = new File(con.getTASKS_PATH()).listFiles();

    public JSONObject makeJSON(String title) throws FileNotFoundException {
        JSONObject temp;
        try {
            temp = (JSONObject) new JSONParser().parse(new FileReader(con.getSAVE_PATH().formatted(title)));
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
        return temp;
    }

    public List<String> getTasks() {
        ArrayList<String> tasks = new ArrayList<>();
        for (File file : this.listOfFiles) {
            if (file.isFile()) {
                tasks.add(file.getName().substring(0, file.getName().indexOf(".json")));
            }
        }
        return tasks;
    }

    public Task toTask(String name) throws FileNotFoundException {
        JSONObject tempObject = makeJSON(name);
        Task task = new Task();
        task.setTitle(tempObject.getAsString("TITLE"));
        task.setDescription(tempObject.getAsString("DESCRIPTION"));
        task.setStatus(Enum.valueOf(Status.class, tempObject.getAsString("STATUS")));
        return task;
    }
}