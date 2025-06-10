package sv.linda.tasks;

import sv.linda.tasks.database.DataBaseFunctions;

public class Constants {
    protected static String TASKS_PATH = "src/main/java/sv/linda/tasks/savedTasks";
    protected static String SAVE_PATH = TASKS_PATH + "/%s.json";
    protected static String BASIC_ERROR = "An error occurred while processing the request: ";
    protected static DataBaseFunctions database = new DataBaseFunctions("mongodb://localhost:27017/", "Tasks");

    public static String TasksPath() {
        return TASKS_PATH;
    }

    public static String SavePath() {
        return SAVE_PATH;
    }

    public static String BasicError() {
        return BASIC_ERROR;
    }

    public static DataBaseFunctions Database() {
        return database;
    }

}