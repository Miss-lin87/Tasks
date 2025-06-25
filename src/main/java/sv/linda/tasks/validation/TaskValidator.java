package sv.linda.tasks.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Task.Task;

import java.util.List;

public class TaskValidator implements Validator, Constants {
    final List<String> nameList;

    public TaskValidator(List<String> nameList) {
        this.nameList = nameList;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Task.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors error) {
        Task task = (Task) target;
        validateTitle(task.getTitle(), error);
        validateDescription(task.getDescription(), error);
    }

    private void validateTitle(String title, Errors error) {
        if (title == null || title.trim().isEmpty()) {
            error.rejectValue(TITLE, "title.empty", "You need to enter a name");
        } else if (nameList.contains(title)) {
            error.rejectValue(TITLE, "title.already.exists", "That task name is in use");
        }
    }

    private void validateDescription(String description, Errors error) {
        if (description == null || description.isEmpty()) {
            error.rejectValue(DESCRIPTION, "description.empty", "You need a description");
        } else if (description.length() < 10) {
            error.rejectValue(DESCRIPTION, "description.too.short", "Description is too short. Minimum 10 characters");
        }
    }

    @Override
    public Errors validateObject(Object target) {
        return Validator.super.validateObject(target);
    }
}