package no.hvl.feedapp.mongodb;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

public class CreateAndInsertCollection {

    public static void main(String[] args) {

        // Default localhost port
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {

            // Giving the name of our database
            String myDataBase = "testDB";
            var database = mongoClient.getDatabase(myDataBase);

            try {
                // Name the collection
                database.createCollection("users");
                System.out.println("Collection created successfully");
            } catch (MongoCommandException e) {
                database.getCollection("users").drop();
            }

            // Create arraylist of data
            var docs = new ArrayList <Document>();

            // Get the collection and populate it with data
            MongoCollection <Document> collection = database.getCollection("users");

            var d1 = new Document("_id", 1);
            d1.append("_firstName", "Ramesh");
            d1.append("_lastName", "Fadatare");
            docs.add(d1);

            var d2 = new Document("_id", 2);
            d2.append("_firstName", "Tony");
            d2.append("_lastName", "Stark");
            docs.add(d2);

            var d3 = new Document("_id", 3);
            d3.append("_firstName", "Tom");
            d3.append("_lastName", "Cruise");
            docs.add(d3);

            var d4 = new Document("_id", 4);
            d4.append("_firstName", "Amir");
            d4.append("_lastName", "Khan");
            docs.add(d4);

            var d5 = new Document("_id", 5);
            d5.append("_firstName", "Umesh");
            d5.append("_lastName", "Fadatare");
            docs.add(d5);

            System.out.println(docs);
            collection.insertMany(docs); // if it's one you can use collection.insertOne(docs)
        }
    }
}
