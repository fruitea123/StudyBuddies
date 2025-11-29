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


    //Connect to Atlas
    public cancel_invitation() {
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


        MongoDatabase db = client.getDatabase("StudyPool");

        invitationsCollection = db.getCollection("StudyPool");
        usersCollection = db.getCollection("Users");
    }

    // Setters
    public void setUserName(String username) { this.username = username; }
    public void setOwnerName(String ownername) { this.ownername = ownername; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    //   cancel only for normal user
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

   //delete invitation for the owner only
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

        //Delete invitation from invitations collection
        invitationsCollection.deleteOne(
                and(
                        eq("ownername", ownername),
                        eq("startTime", startTime),
                        eq("endTime", endTime)
                )
        );

       //remove all users who accepted it
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
