package no.hvl.feedapp.mongodb;

import com.mongodb.client.MongoClients;
import no.hvl.feedapp.model.Poll;
import org.bson.Document;

import java.util.List;

public class MongoDBService {

    public void connectToDatabase() {
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {

            // Must use mongosh first (command: use testDB) if database not yet created
            String myDatabase = "testDB";
            var database = mongoClient.getDatabase(myDatabase);

            System.out.println("database name -> " + database.getName());

            // Print the collection names inside the database
            for (String name: database.listCollectionNames()) {

                System.out.println(name);
            }
        }
    }

    public void createCollection(String collectionName) {
        // TODO: implement
    }

    public void readCollection(String collectionName) {
        // TODO: implement
    }

    public void dropCollection(String collection) {
        // TODO: implement
    }

    public void dropDB() {
        // TODO: implement
    }

    public Document createDoc(Poll poll) {
        Document doc = new Document("pollID", poll.getID());
        doc.append("title", poll.getTitle());
        doc.append("result", poll.getResult());
        return doc;
    }

    public void addDocument (Document document) {
        // TODO: implement
    }

    public void deleteDocument(String collection) {
        // TODO: implement
    }

}
