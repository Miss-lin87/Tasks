package sv.linda.tasks.database;

import com.mongodb.client.*;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataBaseFunctionTest {
    @Mock
    MongoDatabase mockDB;
    @Mock
    MongoCollection<Document> mockCollection;
    @Mock
    FindIterable<Document> iterable;
    @Mock
    MongoCursor<Document> cursor;

    @InjectMocks
    MongoClient mockClient = mock(MongoClient.class);

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
        when(cursor.hasNext()).thenReturn(true);
        when(cursor.next()).thenReturn(mockDock1, mockDock2);
    }

    @Test
    void getAllDataTest() {
        DataBaseFunctions mock = new DataBaseFunctions(mockClient, mockDB);
        when(mock.getAllData(anyString())).thenReturn(mockCollection);
        assertAll(
                () -> assertTrue(mock.getAllData("").find().iterator().hasNext()),
                () -> assertEquals(mockDock1, mock.getAllData("").find().iterator().next()),
                () -> assertEquals(mockDock2, mock.getAllData("").find().iterator().next())
        );
    }
}