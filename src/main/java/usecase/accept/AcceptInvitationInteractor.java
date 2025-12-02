package usecase.accept;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.addToSet;

public class AcceptInvitationInteractor implements AcceptInvitationInputBoundary {
    private final AcceptInvitationOutputBoundary outputBoundary;
    private final MongoCollection<Document> studyPoolCollection;

    /**
     * Connects to MongoDB and initializes the StudyPool collection.
     */
    public AcceptInvitationInteractor(AcceptInvitationOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;

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

    @Override
    public void acceptInvitation(AcceptInvitationInputData inputData) {
        // Step 1: Check for time conflicts with user's existing sessions
        if (hasTimeConflict(inputData.getUsername(), inputData.getSessionOwner())) {
            outputBoundary.prepareFailureView("time conflicts");
            return;
        }

        // Step 2: Find the target session by owner
        Document session = studyPoolCollection.find(eq("owner", inputData.getSessionOwner())).first();

        if (session == null) {
            outputBoundary.prepareFailureView("No session found for owner: " + inputData.getSessionOwner());
            return;
        }

        // Step 3: Add user to participants of the session
        ObjectId id = session.getObjectId("_id");
        studyPoolCollection.updateOne(eq("_id", id), addToSet("participants", inputData.getUsername()));

        // Silent success - no output required
    }

    /**
     * Checks if there is any time overlap between:
     * - The target session whose owner is sessionOwner
     * - Any sessions the user (username) is already participating in
     * Assumes sessions have "startTime" and "endTime" fields stored as Date objects.
     */
    private boolean hasTimeConflict(String username, String sessionOwner) {
        // Fetch the target session times
        Document targetSession = studyPoolCollection.find(eq("owner", sessionOwner)).first();
        if (targetSession == null) return false; // No target session means no conflict

        Date targetStart = targetSession.getDate("startTime");
        Date targetEnd = targetSession.getDate("endTime");
        if (targetStart == null || targetEnd == null) return false; // Missing time info, assume no conflict

        // Fetch all sessions where user participates
        try (MongoCursor<Document> cursor = studyPoolCollection.find(eq("participants", username)).iterator()) {
            while (cursor.hasNext()) {
                Document userSession = cursor.next();

                Date userStart = userSession.getDate("startTime");
                Date userEnd = userSession.getDate("endTime");

                if (userStart == null || userEnd == null) continue; // Skip incomplete session times

                // Check for overlap: true if these intervals overlap
                if (targetStart.before(userEnd) && userStart.before(targetEnd)) {
                    return true; // Conflict found
                }
            }
        }

        return false; // No conflicts found
    }
}
