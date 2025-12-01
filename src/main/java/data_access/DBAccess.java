package data_access;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DBAccess {

    private static final String URI = "mongodb+srv://jessicaanirisaihan_db_user:StudyPoolTestTeam18@studybuddiestest.5iradb0.mongodb.net/?appName=StudyBuddiesTest";
    private static final String DB_NAME = "StudyPool";

    public static MongoDatabase getDatabase() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(URI))
                .serverApi(serverApi)
                .build();

        MongoClient client = MongoClients.create(settings);
        return client.getDatabase(DB_NAME);
    }
}
