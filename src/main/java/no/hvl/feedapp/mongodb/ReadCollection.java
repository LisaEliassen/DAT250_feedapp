package no.hvl.feedapp.mongodb;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;

public class ReadCollection {

    public static void main(String[] args) {

        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {

            // Name of the database
            String myDatabase = "testDB";

            var database = mongoClient.getDatabase(myDatabase);

            // Collection you want to read
            String myCollection = "users";

            MongoCollection <Document> collection = database.getCollection(myCollection);

            try (MongoCursor < Document > cur = collection.find().iterator()) {
                while (cur.hasNext()) {
                    var doc = cur.next();
                    var users = new ArrayList < > (doc.values());

                    System.out.printf("%s: %s%n", users.get(1), users.get(2));
                }
            }
        }
    }
}
