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

    public void uppdateOne(Document data, String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        for (Document doc : collection.find()) {
            if (doc.getString("title").equals(data.getString("title"))) {
                collection.replaceOne(doc, data);
                return;
            }
        }
    }

    public boolean findOne(Document data, String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        for (Document doc : collection.find()) {
            if (doc.getString("title").equals(data.getString("title"))) {
                return true;
            }
        }
        return false;
    }

    public void deleteOne(String name, String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        for (Document doc : collection.find()) {
            if (doc.getString("title").equals(name)) {
                collection.deleteOne(doc);
                return;
            }
        }
    }

    public MongoCollection<Document> getAllData(String collection) {
        return database.getCollection(collection);
    }
}
