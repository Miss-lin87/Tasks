package sv.linda.tasks.constructors;

import lombok.Data;
import sv.linda.tasks.enums.Status;

import java.util.ArrayList;
import java.util.List;

@Data
public class Task {
    private String title;
    private String description;
    private Status status;
    private List<Task> subtasks;

    public Task() {
        this.title = "";
        this.description = "";
        this.status = Status.TODO;
        this.subtasks = new ArrayList<>();
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = Status.TODO;
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

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title +
                ", status=" + status +
                ", description='" + description +
                ", subtasks=" +
                '}' + "\n";
    }
}