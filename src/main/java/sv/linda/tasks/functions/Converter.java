package sv.linda.tasks.functions;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.Document;
import org.springframework.stereotype.Component;
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.constructors.Task.Task;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class Converter {
    protected Gson gson;

    public Converter(Gson gson) {
        this.gson = gson;
    }

    public Login toLogin(Document doc) {
        return gson.fromJson(doc.toJson(), Login.class);
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

    public List<Login> getLogins(MongoCollection<Document> collection) {
        ArrayList<Login> logins = new ArrayList<>();
        for (Document doc : collection.find()) {
            Login login = gson.fromJson(doc.toJson(), Login.class);
            logins.add(login);
        }
        return logins;
    }
}