package sv.linda.tasks.database;

import com.mongodb.client.*;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DataBaseFunctionTest {
    @Mock
    private MongoClient mockClient = mock(MongoClient.class);
    @Mock
    private MongoDatabase mockDB = mock(MongoDatabase.class);
    @Mock
    private MongoCollection<Document> mockCollection = mock(MongoCollection.class);
    @Mock
    private FindIterable<Document> iterable = mock(FindIterable.class);
    @Mock
    private MongoCursor<Document> cursor = mock(MongoCursor.class);
    private final Document mockDock1 = new Document()
            .append("title", "Test 1")
            .append("description", "This is a test document 1");
    private final Document mockDock2 = new Document()
            .append("title", "Test 2")
            .append("description", "This is a test document 2");

    @BeforeEach
    void init() {
        when(mockClient.getDatabase(anyString())).thenReturn(mockDB);
        when(mockDB.getCollection(anyString())).thenReturn(mockCollection);
        when(mockCollection.find()).thenReturn(iterable);
        when(iterable.iterator()).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true, true, false);
        when(cursor.next()).thenReturn(mockDock1, mockDock2);
    }

    @Test
    void getAllDataTest() {
        DataBaseFunctions mock = mock(DataBaseFunctions.class);
        when(mock.getAllData(anyString())).thenReturn(mockCollection);
        assertAll(
                () -> assertTrue(mock.getAllData("").find().iterator().hasNext()),
                () -> assertEquals(mockDock1, mock.getAllData("").find().iterator().next()),
                () -> assertEquals(mockDock2, mock.getAllData("").find().iterator().next())
        );
    }

    @Test
    void findOneTest() {
        DataBaseFunctions mock = mock(DataBaseFunctions.class);
        when(mock.findOne(mockDock1, "test")).thenReturn(true);
        assertAll(
                () -> assertTrue(mock.findOne(mockDock1, "test"))
        );
    }
}