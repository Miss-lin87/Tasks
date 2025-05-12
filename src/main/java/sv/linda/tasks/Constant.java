package sv.linda.tasks;

import lombok.Getter;

@Getter
public class Constant {
    private final String SAVE_PATH = "src/main/java/sv/linda/tasks/savedTasks/%s.json";
    private final String TASKS_PATH = "src/main/java/sv/linda/tasks/savedTasks";
    private final String BASIC_ERROR = "An error occurred while processing the request: ";

    public Constant() {
    }


}
