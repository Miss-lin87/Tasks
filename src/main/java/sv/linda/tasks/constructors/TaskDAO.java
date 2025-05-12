package sv.linda.tasks.constructors;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import sv.linda.tasks.functions.GetInfo;

import java.io.FileNotFoundException;
import java.util.List;

@Repository
public class TaskDAO {
    private final GetInfo info = new GetInfo();
    private final Tasks tasks = new Tasks();
    private List<String> nameList;

    @PostConstruct
    public void init() {
        nameList = info.getTasks();
        for (String name : nameList) {
            try {
                tasks.getTaskList().add(info.toTask(name));
            } catch (FileNotFoundException ignored) {
            }
        }
    }

    public Tasks getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.getTaskList().add(task);
    }
}