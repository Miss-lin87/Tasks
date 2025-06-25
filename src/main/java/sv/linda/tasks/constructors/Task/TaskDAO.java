package sv.linda.tasks.constructors.Task;

import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.springframework.stereotype.Repository;
import sv.linda.tasks.Constants;
import sv.linda.tasks.functions.Converter;

@Repository
public class TaskDAO implements Constants {
    private Converter convert;
    private Tasks tasks;

    public TaskDAO(Converter convert, Tasks tasks) {
        this.convert = convert;
        this.tasks = tasks;
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