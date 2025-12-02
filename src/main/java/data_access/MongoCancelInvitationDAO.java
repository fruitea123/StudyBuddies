package data_access;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.pull;

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
import usecase.cancel.CancelInvitationDataAccessInterface;

/**
 * MongoDB implementation of CancelInvitationDataAccessInterface.
 */
public class MongoCancelInvitationDAO implements CancelInvitationDataAccessInterface {

  /** MongoDB StudyPool collection. */
  private final MongoCollection<Document> studyPoolCollection;

  /**
     * Creates a new DAO and connects to the StudyPool MongoDB database.
  */
  public MongoCancelInvitationDAO() {
    String connectionString =
                "mongodb+srv://jessicaanirisaihan_db_user:"
                        + "StudyPoolTestTeam18@studybuddiestest.5iradb0.mongodb.net/"
                        + "?retryWrites=true&w=majority";

    ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

    MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

    MongoClient client = MongoClients.create(settings);
    MongoDatabase db = client.getDatabase("StudyPool");
    this.studyPoolCollection = db.getCollection("StudyPool");
  }

  /**
     * Finds the invitation where the given user is a participant.
     *
     * @param username participant username
     * @return invitation document or null
   */
  @Override
  public Document findInvitationByParticipant(String username) {
    return studyPoolCollection.find(eq("participants", username)).first();
  }

  /**
     * Finds the invitation created by the owner.
     *
     * @param ownerName owner username
     * @return invitation document or null
  */
  @Override
  public Document findInvitationByOwner(String ownerName) {
    return studyPoolCollection.find(eq("owner", ownerName)).first();
  }

  /**
     * Removes a participant from an invitation.
     *
     * @param doc      invitation document
     * @param username participant to remove
  */
  @Override
   public void removeParticipantFromInvitation(Document doc, String username) {
    ObjectId id = doc.getObjectId("_id");
    studyPoolCollection.updateOne(eq("_id", id), pull("participants", username));
  }

  /**
     * Deletes an invitation entirely.
     *
     * @param doc invitation document
  */
  @Override
  public void deleteInvitation(Document doc) {
    ObjectId id = doc.getObjectId("_id");
    studyPoolCollection.deleteOne(eq("_id", id));
  }
}
