package sv.linda.tasks;

import com.google.gson.Gson;
import org.bson.Document;
import sv.linda.tasks.constructors.Task.Task;
import sv.linda.tasks.database.DataBaseFunctions;

import java.util.List;

public class mainTest {
    private static Document test;

     public static void main(String[] args){
         DataBaseFunctions database = new DataBaseFunctions("mongodb://localhost:27017/", "Tasks");
         //test = Document.parse(new Gson().toJson(new Task("Test", "this is a test")));
         //database.add(List.of(test));
     }
}
