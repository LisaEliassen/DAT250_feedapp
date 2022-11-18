package no.hvl.feedapp.mongodb;

import com.mongodb.client.MongoClients;

public class ConnectToDB {
    public static void main(String args[]) {

        // USEFUL LINK TUTORIAL : https://www.javaguides.net/2019/12/spring-boot-mongodb-crud-example-tutorial.html

        // Default localhost port
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
}
