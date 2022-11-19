package no.hvl.feedapp.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DroppingDB {
    public static void main( String args[] ) {
        //Creating a MongoDB client
        @SuppressWarnings("resource")
        MongoClient mongo = new MongoClient( "localhost" , 27017 );

        // Name of the created database you want to drop
        String myDatabase = "testDB";

        // Get the correct database
        MongoDatabase database = mongo.getDatabase(myDatabase);

        // Drop it
        database.drop();
        System.out.println("Database which is dropped: " + myDatabase );
    }
}
