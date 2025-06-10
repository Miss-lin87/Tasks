package sv.linda.tasks.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.io.FileNotFoundException;
import java.util.List;

public class DataBaseFunctions {
    private String URI;
    private MongoClient client;
    private MongoDatabase database;

    public DataBaseFunctions(String URI, String database) {
        this.URI = URI;
        this.client = MongoClients.create(this.URI);
        this.database = client.getDatabase(database);
    }

    public void addAll(List<Document> data, String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.insertMany(data);
    }

    public void addOne(Document data, String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.insertOne(data);
    }

    public MongoCollection<Document> getAllData(String collection) {
        return database.getCollection(collection);
    }

    public static void main(String[] args) throws FileNotFoundException {
        DataBaseFunctions database = new DataBaseFunctions("mongodb://localhost:27017/", "Tasks");
        MongoCollection<Document> collection = database.database.getCollection("SavedTasks");
    }
}
