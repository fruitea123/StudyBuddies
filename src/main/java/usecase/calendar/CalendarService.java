package usecase.calendar;

/**
 * Provides calendar-related operations for managing study session events.
 */
public interface CalendarService {
  /**
     * Adds a study session event to the calendar.
     *
     * @param session the study session to add
     * @throws Exception if an error occurs while adding the event
  */
  void addEvent(StudySession session) throws Exception;

}


