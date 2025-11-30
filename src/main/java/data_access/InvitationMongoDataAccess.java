package data_access; // data_access/InvitationMongoDataAccess.java
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import entity.Invitation;
import entity.InvitationBuilder;
import entity.User;
import org.bson.Document;
import org.bson.types.ObjectId;
import use_case.accept.AcceptInvitationUserDataAccessInterface;

public class InvitationMongoDataAccess implements AcceptInvitationUserDataAccessInterface {
    private final MongoCollection<Document> invitationsCollection;
    private final MongoCollection<Document> usersCollection;

    public InvitationMongoDataAccess() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = client.getDatabase("StudyPool");
        invitationsCollection = db.getCollection("Invitations");
        usersCollection = db.getCollection("users");
    }

    @Override
    public Invitation fetchInvitationById(String invitationId) {
        Document doc = invitationsCollection.find(Filters.eq("_id", new ObjectId(invitationId))).first();
        if (doc == null) return null;
        // Convert doc to domain Invitation (implement Invitation.fromDocument)
        return Invitation.fromDocument(doc);
    }

    @Override
    public User fetchUserByUsername(String username) {
        Document doc = usersCollection.find(Filters.eq("username", username)).first();
        if (doc == null) return null;
        // Convert doc to domain User (implement User.fromDocument)
        return User.fromDocument(doc);
    }

    @Override
    public boolean hasTimingConflict(String username, String startTime, String endTime) {
        Document conflictDoc = usersCollection.find(
                Filters.and(
                        Filters.eq("username", username),
                        Filters.elemMatch("invitations",
                                Filters.and(
                                        Filters.lt("startTime", endTime),
                                        Filters.gt("endTime", startTime)
                                )
                        )
                )
        ).first();
        return conflictDoc != null;
    }

    @Override
    public boolean addUserToInvitation(String username, String invitationId, Invitation invitationSummary) {
        // Add to user's invitations (addToSet prevents repeat)
        usersCollection.updateOne(
                Filters.eq("username", username),
                Updates.addToSet("invitations", new Document()
                        .append("startTime", invitationSummary.getStartTime())
                        .append("endTime", invitationSummary.getEndTime())
                        .append("ownername", invitationSummary.getOwnername())
                )
        );
        // Add user to invitation participants
        UpdateResult result = invitationsCollection.updateOne(
                Filters.eq("_id", new ObjectId(invitationId)),
                Updates.addToSet("participants", username)
        );
        return result.getModifiedCount() > 0;
    }
}
