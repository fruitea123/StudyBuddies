package use_case.calendar;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import data_access.GoogleCalendarConfig;
import data_access.GoogleCalendarServiceImpl;
import data_access.MongoStudySessionRepository;
import use_case.calendar.InsertUserStudySessionsInteractor;

import java.awt.Desktop;
import java.net.URI;

public class CalendarIntegrationTest {

    public static void main(String[] args) {
        try {
            // MongoDB setup
            String connectionString = "mongodb+srv://jessicaanirisaihan_db_user:StudyPoolTestTeam18@studybuddiestest.5iradb0.mongodb.net/?appName=StudyBuddiesTest";
            MongoClient mongoClient = MongoClients.create(
                    MongoClientSettings.builder()
                            .applyConnectionString(new ConnectionString(connectionString))
                            .build()
            );
            MongoDatabase db = mongoClient.getDatabase("StudyPool");
            MongoCollection collection = db.getCollection("StudyPool");

            var repo = new MongoStudySessionRepository(collection);

            // Google Calendar setup
            var googleCalendarService = new GoogleCalendarServiceImpl(GoogleCalendarConfig.getCalendarService());

            // Use case execution
            var interactor = new InsertUserStudySessionsInteractor(repo, googleCalendarService);
            interactor.execute("steve"); // replace with your username

            System.out.println("✓ All study sessions inserted into Google Calendar!");

            // Open Google Calendar in browser
            Desktop.getDesktop().browse(new URI("https://calendar.google.com/calendar/r"));

            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
