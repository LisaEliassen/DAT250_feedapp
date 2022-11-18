package no.hvl.feedapp.mongodb;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class UpdateDocument {
    public static void main(String[] args) {

        // Creating a Mongo client
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {

            // Name of the database:
            String myDatabase = "testDB";

            // Accessing the database
            MongoDatabase database = mongoClient.getDatabase(myDatabase);

            // Name of the collection you want to update
            String myCollection = "users";

            // Retrieving a collection
            MongoCollection <Document> collection = database.getCollection(myCollection);

            // Updating one collection
            collection.updateOne(new Document("_firstName", "Ramesh"),
                    new Document("$set", new Document("_lastName", "Pawar")));

            // Retrieving the documents after updating
            try (MongoCursor <Document> cur = collection.find().iterator()) {
                while (cur.hasNext()) {
                    var doc = cur.next();
                    var users = new ArrayList < > (doc.values());

                    System.out.printf("%s: %s%n", users.get(1), users.get(2));
                }
            }
        }
    }
}
