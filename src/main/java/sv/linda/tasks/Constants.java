package sv.linda.tasks;

public class Constants {
    protected static String TASKS_PATH = "src/main/java/sv/linda/tasks/savedTasks";
    protected static String SAVE_PATH = TASKS_PATH + "/%s.json";
    protected static String BASIC_ERROR = "An error occurred while processing the request: ";

    public static String TasksPath() {
        return TASKS_PATH;
    }

    public static String SavePath() {
        return SAVE_PATH;
    }

    public static String BasicError() {
        return BASIC_ERROR;
    }

}