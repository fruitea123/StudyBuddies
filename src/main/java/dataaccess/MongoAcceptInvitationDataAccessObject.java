package dataaccess;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.addToSet;

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
import usecase.accept.AcceptInvitationDataAccessInterface;


/**
 * MongoDB implementation of {@link AcceptInvitationDataAccessInterface}.
 *
 * <p>Provides CRUD operations on the study pool collection used by the
 * Accept Invitation use case, including reading sessions and adding
 * participants to invitations.
*/
public class MongoAcceptInvitationDataAccessObject implements AcceptInvitationDataAccessInterface {

  private final MongoCollection<Document> studyPoolCollection;

  /**
     * Creates a new MongoDB data access object for the Accept Invitation use case.
   *
     * <p>Establishes a connection to the StudyPool test cluster using the configured
     * connection string and initializes the underlying study‑pool collection.
  */
  public MongoAcceptInvitationDataAccessObject() {
    String connectionString =
              "mongodb+srv://user:pass@"
                      + "cluster.mongodb.net/"
                      + "StudyPoolTestTeam18?retryWrites=true&w=majority";


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
