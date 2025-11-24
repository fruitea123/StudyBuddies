package use_case.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.calendar.Calendar;

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

    // ----------------- Google Calendar Authorization -----------------
    private static Calendar getCalendarService() throws Exception {
        InputStream in = new FileInputStream("/Users/harish/IdeaProjects/StudyBuddies/src/main/java/use_case/calendar/credentials.json");
        if (in == null) throw new RuntimeException("credentials.json not found!");

        GoogleClientSecrets secrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

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

    // insert events
    public static void insertSampleEvents() throws Exception {
        Calendar service = getCalendarService();

        String[][] events = {
                {"Team Meeting", "2025-11-25", "10:00", "11:00"},
                {"Doctor Appointment", "2025-11-25", "14:00", "15:00"},
                {"Project Deadline", "2025-11-26", "09:00", "09:30"}
        };

        for (String[] e : events) {
            String title = e[0];
            String date = e[1];
            String start = e[2];
            String end = e[3];

            String startIso = date + "T" + start + ":00-05:00";
            String endIso = date + "T" + end + ":00-05:00";

            Event event = new Event()
                    .setSummary(title)
                    .setStart(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(startIso)))
                    .setEnd(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(endIso)));

            service.events().insert("primary", event).execute();
        }
    }

    //Open Google Calendar in Browser
    public static void openGoogleCalendarInBrowser() {
        try {
            String calendarUrl = "https://calendar.google.com/calendar/r"; // primary calendar
            Desktop.getDesktop().browse(new URI(calendarUrl));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            insertSampleEvents();               // Insert sample events
            openGoogleCalendarInBrowser();      // Open calendar in default browser
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
