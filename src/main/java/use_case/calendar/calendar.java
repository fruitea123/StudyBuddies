package use_case.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.Calendar;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;

import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Collections;
import java.util.List;

public class calendar {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);

    /** Connect to Google Calendar API */
    private static Calendar getCalendarService() throws Exception {
        InputStream in = new FileInputStream("src/main/java/use_case/calendar/credentials.json");
        GoogleClientSecrets secrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                secrets,
                SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver.Builder().setPort(8888).build()
        ).authorize("user");

        return new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential
        ).setApplicationName("StudyBuddiesApp").build();
    }

    /** Insert all study sessions where the user is a participant */
    public static void insertUserStudySessions(String username) throws Exception {
        String connectionString =
                "mongodb+srv://jessicaanirisaihan_db_user:StudyPoolTestTeam18@studybuddiestest.5iradb0.mongodb.net/?appName=StudyBuddiesTest";

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase db = mongoClient.getDatabase("StudyPool");
        MongoCollection<Document> invitations = db.getCollection("StudyPool"); // your correct collection

        Calendar service = getCalendarService();

        // Filter invitations where "participants" array contains the username
        Document filter = new Document("participants", new Document("$in", Collections.singletonList(username)));
        MongoCursor<Document> cursor = invitations.find(filter).iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();

            String course = doc.getString("course");
            String description = doc.getString("description");
            String date = doc.getString("date");
            String start = doc.getString("start_time");
            String end   = doc.getString("end_time");

            if (course == null || date == null || start == null || end == null) {
                System.out.println("Skipping invalid document: " + doc.toJson());
                continue;
            }
            String startFixed = start.length() == 5 ? start + ":00" : start;
            String endFixed = end.length() == 5 ? end + ":00" : end;
            String startIso = date + "T" + startFixed + "-05:00";
            String endIso = date + "T" + endFixed + "-05:00";

            Event event = new Event()
                    .setSummary(course)
                    .setDescription(description)
                    .setStart(new EventDateTime().setDateTime(new DateTime(startIso)))
                    .setEnd(new EventDateTime().setDateTime(new DateTime(endIso)));

            service.events().insert("primary", event).execute();

            System.out.println("✓ Added event: " + course + " (" + startIso + " - " + endIso + ")");
        }


        cursor.close();
        mongoClient.close();
    }

    public static void openGoogleCalendarInBrowser() {
        try {
            Desktop.getDesktop().browse(new URI("https://calendar.google.com/calendar/r"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            insertUserStudySessions("steve"); // replace with any username -just for testing
            openGoogleCalendarInBrowser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
