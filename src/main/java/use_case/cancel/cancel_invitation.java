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

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class cancel_invitation {

    private MongoCollection<Document> invitationsCollection;
    private MongoCollection<Document> usersCollection;

    private String username;
    private String ownername;
    private String startTime;
    private String endTime;

    // ---------------------------
    //       CONNECT TO ATLAS
    // ---------------------------
    public cancel_invitation() {

        // ⭐ IMPORTANT: replace username + password
        String connectionString =
                "mongodb+srv://YOUR_USERNAME:YOUR_PASSWORD@studybuddiestest.5iradb0.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        MongoClient client = MongoClients.create(settings);

        // ⭐ DATABASE NAME used in your project
        MongoDatabase db = client.getDatabase("StudyPool");

        invitationsCollection = db.getCollection("StudyPool");
        usersCollection = db.getCollection("Users");
    }

    // Setters
    public void setUserName(String username) { this.username = username; }
    public void setOwnerName(String ownername) { this.ownername = ownername; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    // -------------------------------------------------
    //   CANCEL ONLY FOR THE NORMAL USER ("LEAVE")
    // -------------------------------------------------
    public void leave() {

        // First — find the exact invitation from DB
        Document invitation = invitationsCollection.find(
                and(eq("startTime", startTime), eq("endTime", endTime))
        ).first();

        if (invitation == null) {
            System.out.println(" Invitation not found.");
            return;
        }

        // Remove from user's invitation list
        usersCollection.updateOne(
                eq("username", username),
                pull("invitations",
                        and(
                                eq("startTime", startTime),
                                eq("endTime", endTime)
                        )
                )
        );

        System.out.println("✅ User " + username + " left the invitation.");
    }

    // -------------------------------------------------
    //        DELETE INVITATION (OWNER ONLY)
    // -------------------------------------------------
    public void delete() {

        // First, fetch invitation from DB
        Document invitation = invitationsCollection.find(
                and(
                        eq("ownername", ownername),
                        eq("startTime", startTime),
                        eq("endTime", endTime)
                )
        ).first();

        if (invitation == null) {
            System.out.println(" Owner invitation not found. Cannot delete.");
            return;
        }

        // 1️⃣ Delete invitation from Invitations collection
        invitationsCollection.deleteOne(
                and(
                        eq("ownername", ownername),
                        eq("startTime", startTime),
                        eq("endTime", endTime)
                )
        );

        // 2️⃣ Remove from ALL users who accepted it
        usersCollection.updateMany(
                elemMatch("invitations",
                        and(
                                eq("startTime", startTime),
                                eq("endTime", endTime)
                        )
                ),
                pull("invitations",
                        and(
                                eq("startTime", startTime),
                                eq("endTime", endTime)
                        )
                )
        );

        System.out.println("✅ Invitation deleted for owner + removed from all users.");
    }
}
