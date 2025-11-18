package data_access.mongodbtest;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class StudyPoolAccessTest {
    public static void main( String[] args ) {

        // Replace the placeholder with your MongoDB deployment's connection string
        String uri = "mongodb+srv://jessicaanirisaihan_db_user:StudyPoolTestTeam18@studybuddiestest.5iradb0.mongodb.net/?appName=StudyBuddiesTest";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("StudyPool");
            MongoCollection<Document> collection = database.getCollection("StudyPool");

            Document doc = collection.find(eq("course", "")).first();
            if (doc != null) {
                System.out.println(doc.toJson());
            } else {
                System.out.println("No matching documents found.");
            }
        }
    }
}
