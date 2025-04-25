package sv.linda.tasks.constructors;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Repository;
import sv.linda.tasks.enums.Status;
import sv.linda.tasks.functions.getInfo;

import java.util.List;

@Repository
public class TaskDAO {
    private static final getInfo info = new getInfo();
    private static final Tasks tasks = new Tasks();
    private static final List<String> nameList;

    static {
        nameList = info.getTasks();
        //for (int i = 0; i < nameList.size(); i++) {
        //    tasks.getTaskList().add(info.toTask(nameList.get(i)));
        //}
        //tasks.getTaskList().add(info.toTask(nameList.get(0)));
        for (String name : nameList) {
            tasks.getTaskList().add(info.toTask(name));
        }
    }

    public Tasks getTasks() {
        //for (String name : nameList) {
        //    tasks.getTaskList().add(info.toTask(name));
        //}
        //tasks.getTaskList().add(info.toTask("Task2"));
        return tasks;
    }

    public void addTask(Task task) {
        tasks.getTaskList().add(task);
    }
}