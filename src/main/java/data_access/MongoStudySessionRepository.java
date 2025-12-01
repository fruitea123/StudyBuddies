package data_access;

import com.mongodb.client.*;
import org.bson.Document;
import use_case.calendar.StudySession;
import use_case.calendar.StudySessionRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MongoStudySessionRepository implements StudySessionRepository {

    private final MongoCollection<Document> collection;

    public MongoStudySessionRepository(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    @Override
    public List<StudySession> getSessionsForUser(String username) {
        Document filter = new Document("participants",
                new Document("$in", Collections.singletonList(username)));

        MongoCursor<Document> cursor = collection.find(filter).iterator();
        List<StudySession> sessions = new ArrayList<>();

        while (cursor.hasNext()) {
            Document doc = cursor.next();

            String course = doc.getString("course");
            String description = doc.getString("description");
            String date = doc.getString("date");
            String start = doc.getString("start_time");
            String end   = doc.getString("end_time");

            if (course != null && date != null && start != null && end != null) {
                sessions.add(new StudySession(course, description, date, start, end));
            }
        }
        return sessions;
    }
}
