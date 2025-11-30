package data_access;
//imports
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import use_case.cancel.CancelInvitationDataAccessInterface;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.pull;
public class MongoCancelInvitationDAO implements CancelInvitationDataAccessInterface {

    private final MongoCollection<Document> studyPoolCollection;//Collection

    public MongoCancelInvitationDAO() {

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
    public Document findInvitationByParticipant(String username) {
        return studyPoolCollection.find(eq("participants", username)).first();
    }

    @Override
    public Document findInvitationByOwner(String ownerName) {
        return studyPoolCollection.find(eq("owner", ownerName)).first();
    }

    @Override
    public void removeParticipantFromInvitation(Document doc, String username) {
        ObjectId id = doc.getObjectId("_id");
        studyPoolCollection.updateOne(eq("_id", id), pull("participants", username));
    }

    @Override
    public void deleteInvitation(Document doc) {
        ObjectId id = doc.getObjectId("_id");
        studyPoolCollection.deleteOne(eq("_id", id));
    }
}

