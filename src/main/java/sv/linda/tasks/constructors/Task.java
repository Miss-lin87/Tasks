package sv.linda.tasks.constructors;

import lombok.Getter;
import lombok.Setter;
import sv.linda.tasks.enums.Status;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    public Task(String title, String description) {
        this.title = title;
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