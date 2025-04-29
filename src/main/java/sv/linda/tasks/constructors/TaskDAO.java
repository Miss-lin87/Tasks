package sv.linda.tasks.constructors;

import org.springframework.stereotype.Repository;
import sv.linda.tasks.functions.getInfo;

import java.util.List;

@Repository
public class TaskDAO {
    private static final getInfo info = new getInfo();
    private static final Tasks tasks = new Tasks();
    private static final List<String> nameList;

    static {
        nameList = info.getTasks();
        for (String name : nameList) {
            tasks.getTaskList().add(info.toTask(name));
        }
    }

    public Tasks getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.getTaskList().add(task);
    }
}