package sv.linda.tasks.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import sv.linda.tasks.constructors.Task;
import sv.linda.tasks.functions.getInfo;

import java.util.List;

public class TaskValidator implements Validator {
    private List<String> nameList = new getInfo().getTasks();

    @Override
    public boolean supports(Class clazz) {
        return Task.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors error) {
        Task task = (Task) target;
        if (task.getTitle().isEmpty()) {
            error.rejectValue("title", "title.empty", "You need to enter a name");
        } if (nameList.contains(task.getTitle())) {
            error.rejectValue("title", "title.already.exists" ,"That task name is in use");
        } if (task.getDescription().isEmpty()) {
            error.rejectValue("description", "description.empty", "You need a description");
        } if (task.getDescription().length() < 10) {
            error.rejectValue("description", "description.too.short", "Description is too short. Minimum 10 characters");
        }
    }

    @Override
    public Errors validateObject(Object target) {
        return Validator.super.validateObject(target);
    }
}
