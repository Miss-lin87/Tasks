package sv.linda.tasks.functions;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.Document;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.constructors.Task.Task;
import sv.linda.tasks.database.DataBaseFunctions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Converter {
    protected Gson gson;
    protected DataBaseFunctions database;

    public Converter() {
        gson = new Gson();
        database = Constants.Database();
    }

    protected Task makeTask(File file) throws FileNotFoundException {
        Reader read = new FileReader(file);
        return gson.fromJson(read, Task.class);
    }

    protected Login makeLogin(File file) throws FileNotFoundException {
        Reader read = new FileReader(file);
        return gson.fromJson(read, Login.class);
    }

    public Task toTask(Document doc) {
        return gson.fromJson(doc.toJson(), Task.class);
    }

    public List<String> getTasksNames(MongoCollection<Document> collection) {
        ArrayList<String> tasks = new ArrayList<>();
        for (Document doc : collection.find()) {
            Task task = toTask(doc);
            tasks.add(task.getTitle());
        }
        return tasks;
    }
}