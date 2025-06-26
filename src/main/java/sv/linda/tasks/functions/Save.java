package sv.linda.tasks.functions;

import com.google.gson.Gson;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.constructors.Task.Task;
import sv.linda.tasks.database.DataBaseFunctions;

@Service
public class Save implements Constants {
    private Gson gson;
    private DataBaseFunctions database;

    @Autowired
    public Save(DataBaseFunctions database, Gson gson) {
        this.gson = gson;
        this.database = database;
    }

    private void SaveTask(Task task) {
        Document doc = Document.parse(gson.toJson(task));
        if (database.findOne(doc, TASKS)) {
            database.updateOne(doc, TASKS);
        } else {
            database.addOne(doc, TASKS);
        }
    }

    private void SaveLogin(Login login) {
        Document doc = Document.parse(gson.toJson(login));
        if (database.findOne(doc, USERS)) {
            database.updateOne(doc, USERS);
        } else {
            database.addOne(doc, USERS);
        }
    }

    public <T> void save(T data) {
        switch (data) {
            case Task task -> SaveTask(task);
            case Login login -> SaveLogin(login);
            default -> throw new IllegalArgumentException("Unsupported data type");
        }
    }
}