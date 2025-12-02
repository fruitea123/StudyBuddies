package usecase.calendar;

import java.util.List;

/**
 * Repository interface for retrieving study sessions for a user.
 */
public interface StudySessionRepository {
  /**
     * Returns all study sessions belonging to the specified user.
     *
     * @param username the username whose sessions should be retrieved
     * @return a list of study sessions
  */
  List<StudySession> getSessionsForUser(String username);
}
