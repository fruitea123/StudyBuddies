package data_access;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import entity.User;
import org.bson.Document;
import use_case.signup.SignupUserDataAccessInterface;

import static com.mongodb.client.model.Filters.eq;

public class SignupDAO implements SignupUserDataAccessInterface {

    private final MongoCollection<Document> usersCollection;

    public SignupDAO() {

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

        // Store users inside a Users collection
        this.usersCollection = db.getCollection("Users");
    }

    /**
     * Returns true if a user with the given email exists.
     */
    @Override
    public boolean existsByEmail(String email) {
        Document userDoc = usersCollection.find(eq("email", email)).first();
        return userDoc != null;
    }

    /**
     * Saves a new user document to MongoDB.
     */
    @Override
    public void save(User user) {

        Document doc = new Document()
                // username is the email
                .append("username", user.getEmail())
                .append("passwordHash", user.getPassword())
                .append("firstName", user.getFirstName())
                .append("lastName", user.getLastName())
                .append("dateOfBirth", user.getDateOfBirth())
                .append("programs", user.getPrograms())
                .append("description", user.getDescription())
                .append("icon", user.getIcon());

        usersCollection.insertOne(doc);
    }
}

