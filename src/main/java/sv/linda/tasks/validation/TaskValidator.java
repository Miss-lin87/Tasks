package sv.linda.tasks.validation;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Task.Task;
import sv.linda.tasks.constructors.Task.TaskDAO;

import java.util.ArrayList;
import java.util.List;

public class TaskValidator implements Validator, Constants {
    private final List<Task> nameList;
    private final TaskDAO taskDAO;

    @Autowired
    public TaskValidator(TaskDAO taskDAO) {
        this.nameList = taskDAO.getTasks().getTaskList();
        this.taskDAO = taskDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Task.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors error) {
        Task task = (Task) target;
        validateTitle(task, error);
        validateDescription(task, error);
    }

    private void validateTitle(Task userTask, Errors error) {
        if (userTask.getTitle() == null || userTask.getTitle().trim().isEmpty()) {
            error.rejectValue(TITLE, "title.empty", "You need to enter a name");
        } else if (nameList.stream().anyMatch(task -> task.getTitle().equals(userTask.getTitle()))) {
            error.rejectValue(TITLE, "title.already.exists", "That task name is in use");
        }
    }

    private void validateDescription(Task task, Errors error) {
        if (task.getDescription() == null || task.getDescription().isEmpty()) {
            error.rejectValue(DESCRIPTION, "description.empty", "You need a description");
        } else if (task.getDescription().length() < 10) {
            error.rejectValue(DESCRIPTION, "description.too.short", "Description is too short. Minimum 10 characters");
        }
    }

    @Override
    public Errors validateObject(Object target) {
        return Validator.super.validateObject(target);
    }
}