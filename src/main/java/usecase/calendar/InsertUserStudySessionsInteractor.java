package usecase.calendar;

import java.util.List;

/**
 * Handles inserting a user's study sessions into the calendar
 * by retrieving them from the repository and delegating to the
 * {@link CalendarService}.
 */
public class InsertUserStudySessionsInteractor {

  private final StudySessionRepository repository;
  private final CalendarService calendarService;

  /**
     * Creates an interactor for inserting study sessions into a calendar.
     *
     * @param repository the repository used to retrieve study sessions
     * @param calendarService the calendar service used to add events
   */
  public InsertUserStudySessionsInteractor(StudySessionRepository repository,
                                             CalendarService calendarService) {
    this.repository = repository;
    this.calendarService = calendarService;
  }

  /**
     * Retrieves all study sessions for the given user and adds them
     * to the calendar.
     *
     * @param username the name of the user
     * @throws Exception if adding a session to the calendar fails
  */
  public void execute(String username) throws Exception {
    List<StudySession> sessions = repository.getSessionsForUser(username);

    for (StudySession session : sessions) {
      calendarService.addEvent(session);
    }
  }
}
