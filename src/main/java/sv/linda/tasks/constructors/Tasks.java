package sv.linda.tasks.constructors;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Setter
public class Tasks {
    private List<Task> taskList;

    public List<Task> getTaskList() {
        if (taskList == null) {
            taskList = new ArrayList<>();
        }
        return taskList;
    }
}