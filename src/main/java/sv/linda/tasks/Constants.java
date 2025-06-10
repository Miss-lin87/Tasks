package sv.linda.tasks;

import sv.linda.tasks.database.DataBaseFunctions;

public class Constants {
    protected static String BASIC_ERROR = "An error occurred while processing the request: ";
    protected static DataBaseFunctions database = new DataBaseFunctions("mongodb://localhost:27017/", "Tasks");

    public static String BasicError() {
        return BASIC_ERROR;
    }

    public static DataBaseFunctions Database() {
        return database;
    }

}