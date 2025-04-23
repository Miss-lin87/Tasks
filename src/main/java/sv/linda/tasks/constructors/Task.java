package sv.linda.tasks.constructors;

import lombok.Getter;
import sv.linda.tasks.enums.Status;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Task {
    private String title;
    private Status status;
    private String description;
    private List<Task> subtasks;

    public Task() {
        this.title = "";
        this.status = Status.TODO;
        this.description = "";
        this.subtasks = new ArrayList<>();
    }

    public Task(String name, String description) {
        this.title = name;
        this.status = Status.TODO;
        this.description = description;
        this.subtasks = new ArrayList<>();
    }

    public void changeStatus(Status status) {
        this.status = status;
    }

    public void addSubtask(Task subtask) {
        this.subtasks.add(subtask);
    }

    public void removeSubtask(Task subtask) {
        this.subtasks.remove(subtask);
    }
}
