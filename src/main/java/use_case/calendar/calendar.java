package use_case.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
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
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.List;

public class calendar {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);

    //Google Calendar Authorization
    private static Calendar getCalendarService() throws Exception {
        InputStream in = new FileInputStream("/Users/harish/IdeaProjects/StudyBuddies/src/main/java/use_case/calendar/credentials.json");
        if (in == null) throw new RuntimeException("credentials.json not found!");

        GoogleClientSecrets secrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                secrets,
                SCOPES
        )
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        return new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential
        )
                .setApplicationName("SwingGoogleCalendar")
                .build();
    }

    // ----------------- Insert events from MongoDB -----------------
    public static void insertSampleEvents() throws Exception {

        // ----------------- MONGODB CONNECTION -----------------
        String connectionString =
                "mongodb+srv://jessicaanirisaihan_db_user:<db_password>@studybuddiestest.5iradb0.mongodb.net/?appName=StudyBuddiesTest";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);

        // Your actual DB + Collection names:
        MongoDatabase db = mongoClient.getDatabase("StudyPool");
        MongoCollection<Document> invitations = db.getCollection("StudyPool");

        // google calendar connection
        Calendar service = getCalendarService();

        //read mongo docs and insert events
        MongoCursor<Document> cursor = invitations.find().iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();

            String course = doc.getString("course");
            String date = doc.getString("date");
            String start = doc.getString("startTime");
            String end   = doc.getString("endTime");

            if (course == null || date == null || start == null || end == null) {
                System.out.println("Skipping document (missing fields): " + doc.toJson());
                continue;
            }

            String startIso = date + "T" + start + ":00-05:00";
            String endIso   = date + "T" + end + ":00-05:00";

            Event event = new Event()
                    .setSummary(course)
                    .setStart(new EventDateTime().setDateTime(new DateTime(startIso)))
                    .setEnd(new EventDateTime().setDateTime(new DateTime(endIso)));

            service.events().insert("primary", event).execute();

            System.out.println("Added event → " + course + " (" + start + " - " + end + ")");
        }

        mongoClient.close();
    }

    //Open Calendar
    public static void openGoogleCalendarInBrowser() {
        try {
            Desktop.getDesktop().browse(new URI("https://calendar.google.com/calendar/r"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // main
    public static void main(String[] args) {
        try {
            insertSampleEvents();        // Now loads from MongoDB
            openGoogleCalendarInBrowser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
