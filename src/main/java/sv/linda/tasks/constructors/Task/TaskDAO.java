package sv.linda.tasks.constructors.Task;

import com.mongodb.client.MongoCollection;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.springframework.stereotype.Repository;
import sv.linda.tasks.Constants;
import sv.linda.tasks.database.DataBaseFunctions;
import sv.linda.tasks.functions.Converter;

@Repository
public class TaskDAO {
    private final DataBaseFunctions database = new DataBaseFunctions("mongodb://localhost:27017/", "Tasks");
    private final Converter convert = new Converter();
    //private final GetInfo info = new GetInfo(new File(Constants.TasksPath()).listFiles());
    private final Tasks tasks = new Tasks();

    @PostConstruct
    public void init() {
        for (Document doc : database.getAllData("SavedTasks").find()) {
            Task task = convert.toTask(doc);
            tasks.getTaskList().add(task);
        }
    }

    public Tasks getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.getTaskList().add(task);
    }
}