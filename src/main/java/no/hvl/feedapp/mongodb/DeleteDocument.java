package no.hvl.feedapp.mongodb;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class DeleteDocument {

    public static void main(String[] args) {
        // Creating a Mongo client
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {

            // Name of your database
            String myDatabase = "testDB";

            // Accessing the database
            MongoDatabase database = mongoClient.getDatabase(myDatabase);

            // The collection you need
            String myCollection = "users";

            // Retrieving a collection
            MongoCollection <Document> collection = database.getCollection(myCollection);

            // Deleting the document with id value 1
            collection.deleteOne(Filters.eq("_id", 1));
            System.out.println("Document deleted successfully...");

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
