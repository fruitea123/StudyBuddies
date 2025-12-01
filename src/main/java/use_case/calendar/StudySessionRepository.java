package use_case.calendar;

import java.util.List;

public interface StudySessionRepository {
    List<StudySession> getSessionsForUser(String username);
}
