package sv.linda.tasks;

import sv.linda.tasks.database.DataBaseFunctions;

public interface Constants {
    String BASIC_ERROR = "An error occurred while processing the request: ";
    DataBaseFunctions database = new DataBaseFunctions("mongodb://localhost:27017/", "Tasks");
}