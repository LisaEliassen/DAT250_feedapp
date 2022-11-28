package no.hvl.feedapp.mongodb;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import no.hvl.feedapp.model.Poll;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDBService {

    private MongoDatabase database;

    public MongoDBService () {

            var mongoClient = MongoClients.create("mongodb://localhost:27017");

            // Must use mongosh first (command: use testDB) if database not yet created
            String myDatabase = "testDB";
            this.database = mongoClient.getDatabase(myDatabase);

            //System.out.println("database name -> " + this.database.getName());

            try {
                boolean collectionExists = this.database.listCollectionNames()
                        .into(new ArrayList<>()).contains("test");
                // Name the collection
                if (!collectionExists) {
                    this.database.createCollection("test");
                    System.out.println("Collection created successfully");
                }
            } catch (MongoCommandException e) {
                this.database.getCollection("test").drop();
            }

            // Print the collection names inside the database
            /*for (String name: this.database.listCollectionNames()) {
                System.out.println(name);
            }*/

    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }

    public void dropDB(String myDatabase) {
        // Drop it
        this.database.drop();
        System.out.println("Database which is dropped: " + myDatabase );
    }

    public MongoCollection<Document> readCollection(String myCollection) {
        MongoCollection<Document> collection = this.database.getCollection(myCollection);

        return collection;
    }

    public void dropCollection(String myCollection) {
        // Get a collection
        MongoCollection<Document> collection = this.database.getCollection(myCollection);
        System.out.println("Collection name -> " + collection);

        // Dropping a Collection
        collection.drop();
        System.out.println("Collection dropped successfully");
    }

    public Document createDoc(Poll poll) {
        Document doc = new Document("pollID", poll.getID());
        doc.append("title", poll.getTitle());
        doc.append("result", poll.getResult());
        return doc;
    }

    public void addOneDocument (Document document, String myCollection) {
        // Get the collection and populate it with data
        MongoCollection <Document> collection = this.database.getCollection(myCollection);
        collection.insertOne(document); // if it's one you can use collection.insertOne(docs)
    }

    public void addManyDocuments (ArrayList<Document> docs, String myCollection) {
        // Get the collection and populate it with data
        MongoCollection <Document> collection = this.database.getCollection(myCollection);
        collection.insertMany(docs);
    }

    public void deleteDocument(String myCollection, Long PollID) {
        // Retrieving a collection
        MongoCollection <Document> collection = this.database.getCollection(myCollection);

        // Deleting the document with certain pollID
        collection.deleteOne(Filters.eq("pollID", PollID));
        System.out.println("Document deleted successfully...");
    }

    public Document getDocument(String myCollection, Long pollID) {
        MongoCollection <Document> collection = this.database.getCollection(myCollection);
        Document doc = collection.find(new Document("PollID", pollID)).first();
        return doc;
    }

    public ArrayList<Object> updateDocument(String myCollection, Document newDocument, Document oldDocument) {
        // Retrieving a collection
        MongoCollection <Document> collection = this.database.getCollection(myCollection);

        // Updating one collection
        collection.updateOne(oldDocument,
                new Document("$set", newDocument));

        // Retrieving the documents after updating
        try (MongoCursor <Document> cur = collection.find().iterator()) {
            while (cur.hasNext()) {
                var doc = cur.next();
                var poll = new ArrayList <> (doc.values());
                System.out.printf("PollID: %s. Title: %s. Result: %s.%n", poll.get(1), poll.get(2), poll.get(3));
                return poll;
            }
        }
        return null;
    }

}
