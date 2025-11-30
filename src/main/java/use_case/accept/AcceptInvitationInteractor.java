package use_case.accept;

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

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.addToSet;

public class AcceptInvitationInteractor {
    private final MongoCollection<Document> studyPoolCollection;

    /**
     * Connects to MongoDB and initializes the StudyPool collection.
     */
    public AcceptInvitationInteractor() {
        String connectionString =
                "mongodb+srv://jessicaanirisaihan_db_user:StudyPoolTestTeam18@studybuddiestest.5iradb0.mongodb.net/?retryWrites=true&w=majority";

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

    /**
     * Accepts the invitation by adding username to the participants of the owner's session.
     * Makes changes on backend only, no output returned.
     */
    public void acceptInvitation(String sessionOwner, String username) {
        Document session = studyPoolCollection.find(eq("owner", sessionOwner)).first();

        if (session == null) {
            // Session not found, silently ignore or you can throw an exception if you prefer
            return;
        }

        ObjectId id = session.getObjectId("_id");
        studyPoolCollection.updateOne(eq("_id", id), addToSet("participants", username));
    }
}
