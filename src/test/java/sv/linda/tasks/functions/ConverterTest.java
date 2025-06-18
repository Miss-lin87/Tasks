package sv.linda.tasks.functions;

import com.mongodb.client.*;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.constructors.Task.Task;
import sv.linda.tasks.enums.Status;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConverterTest extends Converter {
    @Mock
    private MongoCollection<Document> mockList = mock(MongoCollection.class);
    @Mock
    private FindIterable<Document> iterable = mock(FindIterable.class);
    @Mock
    private MongoCursor<Document> cursor = mock(MongoCursor.class);
    private final Document mockTaskDoc = new Document();
    private final Document mockLogin1Dock = new Document();
    private final Document mockLogin2Dock = new Document();


    private void mockTaskCollection() {
        when(mockList.find()).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true,false);
        when(cursor.next()).thenReturn(mockTaskDoc);
    }

    private void mockLoginCollection() {
        when(mockList.find()).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true,true,false);
        when(cursor.next()).thenReturn(mockLogin1Dock, mockLogin2Dock);
    }

    @BeforeEach
    public void init() {
        mockTaskDoc.append("title","Task1")
                .append("description",  "This is the test for a task")
                .append("status", "IN_PROGRESS")
                .append("subtasks", new ArrayList<>());
        mockLogin1Dock.append("username", "admin")
                .append("password", "admin1");
        mockLogin2Dock.append("username", "user")
                .append("password", "user123");
    }

    @Test
    public void testToTask() {
        Task temp = toTask(mockTaskDoc);
        assertAll(
                () -> assertNotNull(temp),
                () -> assertEquals(Task.class, temp.getClass()),
                () -> assertEquals("Task1", temp.getTitle()),
                () -> assertEquals("This is the test for a task", temp.getDescription()),
                () -> assertEquals(Status.IN_PROGRESS, temp.getStatus())
        );
    }

    @Test
    public void testToLogin() {
        Login temp = toLogin(mockLogin1Dock);
        assertAll(
                () -> assertNotNull(temp),
                () -> assertEquals("admin", temp.getUsername()),
                () -> assertEquals("admin1", temp.getPassword()),
                () -> assertEquals(Login.class, temp.getClass())
        );
    }

    @Test
    public void testGetTaskNames() {
        List<String> result = new ArrayList<>(List.of("Task1"));
        assertAll(
                () -> assertNotNull(mockList), this::mockTaskCollection,
                () -> assertEquals(result, getTasksNames(mockList)), this::mockTaskCollection,
                () -> assertTrue(getTasksNames(mockList).contains("Task1"))
        );
    }

    @Test
    public void testGetLogins() {
        List<Login> result = new ArrayList<>(List.of(new Login("admin", "admin1"), new Login("user", "user123")));
        assertAll(
                () -> assertNotNull(mockList), this::mockLoginCollection,
                () -> assertEquals(result, getLogins(mockList)), this::mockLoginCollection,
                () -> assertTrue(getLogins(mockList).contains(result.getFirst()))
        );
    }
}