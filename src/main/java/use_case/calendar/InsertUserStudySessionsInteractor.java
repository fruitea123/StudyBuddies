package use_case.calendar;

import java.util.List;

public class InsertUserStudySessionsInteractor {

    private final StudySessionRepository repository;
    private final CalendarService calendarService;

    public InsertUserStudySessionsInteractor(StudySessionRepository repository,
                                             CalendarService calendarService) {
        this.repository = repository;
        this.calendarService = calendarService;
    }

    public void execute(String username) throws Exception {
        List<StudySession> sessions = repository.getSessionsForUser(username);

        for (StudySession session : sessions) {
            calendarService.addEvent(session);
        }
    }
}
