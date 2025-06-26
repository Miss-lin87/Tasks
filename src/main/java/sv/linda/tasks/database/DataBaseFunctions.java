package sv.linda.tasks.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.annotation.PreDestroy;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sv.linda.tasks.Constants;

@Service
public class DataBaseFunctions implements Constants {
    private final MongoDatabase database;
    private final MongoClient client;

    @Autowired
    public DataBaseFunctions(MongoClient client, MongoDatabase database) {
        this.client = client;
        this.database = database;
    }

    @PreDestroy
    public void clean() {
        client.close();
    }

    public void addOne(Document data, String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.insertOne(data);
    }

    public void updateOne(Document data, String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        for (Document doc : collection.find()) {
            if (doc.getString(TITLE).equals(data.getString(TITLE))) {
                collection.replaceOne(doc, data);
                return;
            }
        }
    }

    public boolean findOne(Document data, String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        for (Document doc : collection.find()) {
            if (doc.equals(data)) {
                return true;
            }
        }
        return false;
    }

    public void deleteOne(String name, String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        for (Document doc : collection.find()) {
            if (doc.getString(TITLE).equals(name)) {
                collection.deleteOne(doc);
                return;
            }
        }
    }

    public MongoCollection<Document> getAllData(String collection) {
        return database.getCollection(collection);
    }
}