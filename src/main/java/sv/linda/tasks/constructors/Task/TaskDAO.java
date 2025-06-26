package sv.linda.tasks.constructors.Task;

import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.linda.tasks.Constants;
import sv.linda.tasks.database.DataBaseFunctions;
import sv.linda.tasks.functions.Converter;

@Service
public class TaskDAO implements Constants {
    private Converter convert;
    private Tasks tasks;
    private DataBaseFunctions database;

    @Autowired
    public TaskDAO(Converter convert, Tasks tasks, DataBaseFunctions database) {
        this.convert = convert;
        this.tasks = tasks;
        this.database = database;
    }

    @PostConstruct
    public void init() {
        for (Document doc : database.getAllData(TASKS).find()) {
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