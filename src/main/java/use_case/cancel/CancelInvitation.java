package use_case.cancel;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Updates.pull;
import static com.mongodb.client.model.Filters.eq;


/**
 * Provides methods for a user to leave a study session or an owner to delete a session.
 */
public class CancelInvitation {
  private final MongoCollection<Document> studyPoolCollection;
  private String username;
  private String ownerName;

    /**
     * Connects to the MongoDB Atlas StudyPool database and retrieves the StudyPool collection.
     */
    public CancelInvitation() {
        String connectionString =
            "mongodb+srv://jessicaanirisaihan_db_user:StudyPoolTestTeam18@studybuddiestest.5iradb0.mongodb.net/?retry"
                    + "Writes=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        MongoClient client = MongoClients.create(settings);
        MongoDatabase db = client.getDatabase("StudyPool");
        studyPoolCollection = db.getCollection("StudyPool");
    }

    /** Sets the username for a user leaving a session. */
    public void setUsername(String username) {
        this.username = username;
    }

    /** Sets the owner name for deleting a session. */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * Allows a user to leave a study session.
     * The user is removed from the participants array of the session.
     */
    public void leave() {
        Document invitation = studyPoolCollection.find(eq("participants", username)).first();

        if (invitation == null) {
            System.out.println("No session found for user: " + username);
            return;
        }

        ObjectId id = invitation.getObjectId("_id");
        studyPoolCollection.updateOne(eq("_id", id), pull("participants", username));

        System.out.println("User " + username + " left session with ID: " + id);
    }

    /**
     * Allows the owner to delete the entire study session.
     * The document corresponding to the owner is removed from the collection.
     */
    public void delete() {
        Document invitation = studyPoolCollection.find(eq("owner", ownerName)).first();

        if (invitation == null) {
            System.out.println("No session found for owner: " + ownerName);
            return;
        }

        ObjectId id = invitation.getObjectId("_id");
        studyPoolCollection.deleteOne(eq("_id", id));

        System.out.println("Owner " + ownerName + " deleted session with ID: " + id);
    }

    /** Main method for testing the leave and delete functionality. */
    public static void main(String[] args) {
        CancelInvitation test = new CancelInvitation();


        test.setOwnerName("john");//testing
        test.delete();


        test.setUsername("max");//testing
        test.leave();

        System.out.println("Test complete.");
    }
}
