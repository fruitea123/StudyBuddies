package data_access;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import use_case.calendar.CalendarService;
import use_case.calendar.StudySession;
//imports
/**
 * Google Calendar API implementation of {@link CalendarService}.
 */
public class GoogleCalendarServiceImpl implements CalendarService {

  /** Google Calendar API client. */
  private final Calendar calendar;

  /**
     * Creates a GoogleCalendarServiceImpl using the given Calendar client.
     *
     * @param calendar Google Calendar service instance
   */
  public GoogleCalendarServiceImpl(Calendar calendar) {
    this.calendar = calendar;
  }

  /**
     * Adds an event to Google Calendar for the given study session.
     *
     * @param session study session details
     * @throws Exception if the Calendar API fails
  */
  @Override
  public void addEvent(StudySession session) throws Exception {
    String startIso = session.getDate() + "T" + session.getStartTime() + ":00-05:00";
    String endIso = session.getDate() + "T" + session.getEndTime() + ":00-05:00";

    Event event = new Event()
                .setSummary(session.getCourse())
                .setDescription(session.getDescription())
                .setStart(new EventDateTime().setDateTime(new DateTime(startIso)))
                .setEnd(new EventDateTime().setDateTime(new DateTime(endIso)));

    calendar.events().insert("primary", event).execute();
  }
}
