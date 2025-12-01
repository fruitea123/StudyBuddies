package data_access;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import use_case.accept.AcceptInvitationUserDataAccessInterface;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.addToSet;

public class MongoAcceptInvitationDAO implements AcceptInvitationUserDataAccessInterface {

    private final MongoCollection<Document> studyPoolCollection;

    // database connection (same as cancel DAO)
    public MongoAcceptInvitationDAO() {
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

    // find invitation by its _id (string form)
    @Override
    public Document findInvitationById(String invitationId) {
        ObjectId id = new ObjectId(invitationId);
        return studyPoolCollection.find(eq("_id", id)).first();
    }

    // optional: find invitation by owner (parallel to cancel DAO)
    @Override
    public Document findInvitationByOwner(String ownerName) {
        return studyPoolCollection.find(eq("owner", ownerName)).first();
    }

    // all invitations where this user is already a participant
    @Override
    public Iterable<Document> findInvitationsByParticipant(String username) {
        return studyPoolCollection.find(eq("participants", username));
    }

    // add user to participants array (no duplicates)
    @Override
    public void addParticipantToInvitation(Document doc, String username) {
        ObjectId id = doc.getObjectId("_id");
        studyPoolCollection.updateOne(eq("_id", id), addToSet("participants", username));
    }
}
