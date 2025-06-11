package sv.linda.tasks.functions;

import com.google.gson.Gson;
import org.bson.Document;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.constructors.Task.Task;

public class Save implements Constants {
    protected final Gson gson;

    public Save() {
        gson = new Gson();
    }

    private void SaveTask(Task task) {
        Document doc = Document.parse(gson.toJson(task));
        if (database.findOne(doc, "SavedTasks")) {
            database.updateOne(doc, "SavedTasks");
        } else {
            database.addOne(doc, "SavedTasks");
        }
    }

    private void SaveLogin(Login login) {
        Document doc = Document.parse(gson.toJson(login));
        if (database.findOne(doc, "Logins")) {
            database.updateOne(doc, "Logins");
        } else {
            database.addOne(doc, "Logins");
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