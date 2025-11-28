package data_access;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import entity.Invitation;
import entity.User;
import org.bson.Document;
import use_case.make_invitation.MakeInvitationDataAccessInterface;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MongoInvitationDataAccessObject implements MakeInvitationDataAccessInterface {

    // 1) Connection string from Atlas
    //    (later you can read this from an env var)
    private static final String CONNECTION_STRING =
            "mongodb+srv://jessicaanirisaihan_db_user:<YOUR_PASSWORD_HERE>@studybuddiestest.5iradb0.mongodb.net/?appName=StudyBuddiesTest";

    // 2) Names for database + collection (match what you see in Atlas)
    private static final String DATABASE_NAME   = "StudyPool";   // <- check left sidebar in Data Explorer
    private static final String COLLECTION_NAME = "StudyPool";   // <- the collection you see under that DB

    private final MongoClient client;
    private final MongoCollection<Document> invitations;

    public MongoInvitationDataAccessObject() {
        this.client = MongoClients.create(CONNECTION_STRING);
        MongoDatabase db = client.getDatabase(DATABASE_NAME);
        this.invitations = db.getCollection(COLLECTION_NAME);
    }

    // region Mapping helpers --------------------------------------------------

    private Document toDocument(Invitation inv) {
        Document doc = new Document();
        doc.append("course", inv.getCourse());
        doc.append("description", inv.getDescription());
        doc.append("date", inv.getDate().toString());          // store as ISO string "2025-11-26"
        doc.append("startTime", inv.getStartTime().toString()); // "15:00"
        doc.append("endTime", inv.getEndTime().toString());
        doc.append("mode", inv.getMode());
        doc.append("location", inv.getLocation());
        doc.append("capacity", inv.getCapacity());
        doc.append("owner", inv.getOwner().getName());          // you can later also store ownerId
        // participants as list of usernames:
        List<String> participantNames = new ArrayList<>();
        for (User u : inv.getParticipants()) {
            participantNames.add(u.getName());
        }
        doc.append("participants", participantNames);

        return doc;
    }

    private Invitation fromDocument(Document doc) {
        // You can implement this later if you need to read invitations back.
        // For now, not strictly necessary for MakeInvitation use case.
        throw new UnsupportedOperationException("fromDocument not implemented yet");
    }

    // endregion

    // region MakeInvitationDataAccessInterface methods ------------------------

    @Override
    public void save(Invitation inv) {
        invitations.insertOne(toDocument(inv));
    }

    @Override
    public boolean existsOverlap(String course, LocalDate date,
                                 LocalTime start, LocalTime end) {

        // Find same course + date first
        List<Document> docs = invitations.find(
                Filters.and(
                        Filters.eq("course", course),
                        Filters.eq("date", date.toString())
                )
        ).into(new ArrayList<>());

        for (Document doc : docs) {
            LocalTime otherStart = LocalTime.parse(doc.getString("startTime"));
            LocalTime otherEnd   = LocalTime.parse(doc.getString("endTime"));

            boolean overlap =
                    start.isBefore(otherEnd) && otherStart.isBefore(end);

            if (overlap) return true;
        }
        return false;
    }

    @Override
    public boolean ownerHasOverlap(User owner, LocalDate date,
                                   LocalTime start, LocalTime end) {

        List<Document> docs = invitations.find(
                Filters.and(
                        Filters.eq("owner", owner.getName()),
                        Filters.eq("date", date.toString())
                )
        ).into(new ArrayList<>());

        for (Document doc : docs) {
            LocalTime otherStart = LocalTime.parse(doc.getString("startTime"));
            LocalTime otherEnd   = LocalTime.parse(doc.getString("endTime"));

            boolean overlap =
                    start.isBefore(otherEnd) && otherStart.isBefore(end);

            if (overlap) return true;
        }
        return false;
    }

    @Override
    public List<Invitation> listByCourseAndDate(String course, LocalDate date) {
        // Optional for your use case; can implement later if needed.
        return List.of();
    }

    // You can also add a close() method to close the client when app shuts down.
}