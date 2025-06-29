package sv.linda.tasks.functions;

import com.google.gson.Gson;
import com.mongodb.client.*;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.constructors.Task.Task;
import sv.linda.tasks.enums.Status;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConverterTest {
    @Mock
    FindIterable<Document> iterable;
    @Mock
    MongoCursor<Document> cursor;
    @InjectMocks
    MongoCollection<Document> mockList = mock(MongoCollection.class);

    private final Converter converter = new Converter(new Gson());
    private final Document mockTaskDoc = new Document()
            .append("title","Task1")
            .append("description",  "This is the test for a task")
            .append("status", "IN_PROGRESS")
            .append("subtasks", new ArrayList<>());
    private final Document mockLogin1Dock = new Document()
            .append("username", "admin")
            .append("password", "admin1");;
    private final Document mockLogin2Dock = new Document()
            .append("username", "user")
            .append("password", "user123");;


    private void mockTaskCollection() {
        when(mockList.find()).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true, false);
        when(cursor.next()).thenReturn(mockTaskDoc);
    }

    private void mockLoginCollection() {
        when(mockList.find()).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true, true, false);
        when(cursor.next()).thenReturn(mockLogin1Dock, mockLogin2Dock);
    }

    @BeforeEach
    public void init() {
        //when(mockList.find()).thenReturn(iterable);
        //when(iterable.iterator()).thenReturn(cursor);
        //when(cursor.hasNext()).thenReturn(true);
    }

    @Test
    public void testToTask() {
        Task temp = converter.toTask(mockTaskDoc);
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
        Login temp = converter.toLogin(mockLogin1Dock);
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
                () -> assertEquals(result, converter.getTasksNames(mockList)), this::mockTaskCollection,
                () -> assertTrue(converter.getTasksNames(mockList).contains("Task1"))
        );
    }

    @Test
    public void testGetLogins() {
        List<Login> result = new ArrayList<>(List.of(new Login("admin", "admin1"), new Login("user", "user123")));
        assertAll(
                () -> assertNotNull(mockList), this::mockLoginCollection,
                () -> assertEquals(result, converter.getLogins(mockList)), this::mockLoginCollection,
                () -> assertTrue(converter.getLogins(mockList).contains(result.getFirst()))
        );
    }
}