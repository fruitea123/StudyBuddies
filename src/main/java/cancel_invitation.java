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

    public cancel_invitation()
    {
        MongoClient client=MongoClients.create("mongodb://localhost:27017");//(sample localhost) this would change based on which laptop the db is created
        MongoDatabase database=client.getDatabase("StudyPool");//our database name which is studypool
        invitationsCollection=database.getCollection("Invitations");
        usersCollection=database.getCollection("users");
    }
    public void setUserName(String username) { this.username = username; }
    public void setOwnerName(String ownername) { this.ownername = ownername; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public void leave()
    {
        //removes from user's invitation list
        usersCollection.updateOne(eq("username",username),
                pull("invitations",and(
                        eq("startTime",startTime),
                        eq("endTime",endTime)
                  )
                )
        );


    }



}
