package sv.linda.tasks.functions;

import com.google.gson.Gson;
import org.bson.Document;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.constructors.Task.Task;
import sv.linda.tasks.database.DataBaseFunctions;

import javax.print.Doc;
import java.io.*;

public class Save {
    protected final Gson gson;
    private final DataBaseFunctions database;

    public Save() {
        gson = new Gson();
        database = Constants.Database();
    }

    private void SaveTask(Task task) {
        Document doc = Document.parse(gson.toJson(task));
        database.addOne(doc, "SavedTasks");
    }

    private void SaveLogin(Login login) {
        Document doc = Document.parse(gson.toJson(login));
        database.addOne(doc, "Logins");
    }

    public <T> void save(T data) throws IOException {
        if (data instanceof Task) {
            SaveTask((Task) data);
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }
}