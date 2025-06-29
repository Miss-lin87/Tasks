package sv.linda.tasks.functions;

import com.google.gson.Gson;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import sv.linda.tasks.Constants;
import sv.linda.tasks.constructors.Task.Task;
import sv.linda.tasks.database.DataBaseFunctions;

import java.util.Properties;

import static org.mockito.Mockito.when;


public class SaveTest {
    private DataBaseFunctions mockDB = Mockito.mock(DataBaseFunctions.class);
    private Gson gson = Mockito.mock(Gson.class);
    private Save save;

    @BeforeEach
    public void init() {
        save = new Save(mockDB, gson);
    }

    @Test
    public void testSetUp() {

    }

    @Test
    public void testSaveTask() {
        Task temp = new Task("Test", "This is a test");
        Document tempDoc = Document.parse(new Gson().toJson(temp));
        when(mockDB.findOne(tempDoc, Constants.TASKS)).thenReturn(false);
    }
}