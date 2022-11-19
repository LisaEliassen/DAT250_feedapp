package no.hvl.feedapp.mongodb;

import org.bson.Document;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DropCollection {

    public static void main(String[] args) {

        // Creating a Mongo client
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {

            // Name of your database
            String myDatabase = "testDB";

            // Accessing the database
            MongoDatabase database = mongoClient.getDatabase(myDatabase);

            // Create a collection, don't really need this atm cause of CreateAndInsertCollection class
            //database.createCollection("sampleCollection");
            //System.out.println("Collections created successfully");

            // Name of collection you wish to drop
            String myCollection = "users";

            // Get a collection
            MongoCollection <Document> collection = database.getCollection(myCollection);
            System.out.println("Collection name -> " + collection);

            // Dropping a Collection
            collection.drop();
            System.out.println("Collection dropped successfully");
        }
    }
}
