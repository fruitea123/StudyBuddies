package data_access;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import entity.Invitation;
import entity.User;
import mapper.DBInvitationMapper;
import org.bson.Document;
import org.bson.types.ObjectId;
import use_case.accept.AcceptInvitationUserDataAccessInterface;
import use_case.cancel.CancelInvitationDataAccessInterface;
import use_case.filter.FilterInvitationDataAccessInterface;
import use_case.make_invitation.MakeInvitationDataAccessInterface;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.addToSet;
import static com.mongodb.client.model.Updates.pull;

/**
 * MongoDB-backed DAO for invitations.
 * <p>
 * Implements:
 * - filter use case
 * - accept invitation use case
 * - cancel invitation use case
 * - make invitation use case
 */
public class InvitationDAO implements FilterInvitationDataAccessInterface,
  CancelInvitationDataAccessInterface,
  AcceptInvitationUserDataAccessInterface,
  MakeInvitationDataAccessInterface {

  /** Collection storing invitations in MongoDB. */
  private final MongoCollection<Document> studyPoolCollection;

  /** Main constructor: pass in a MongoDatabase. */
  public InvitationDAO(MongoDatabase db) {
    this.studyPoolCollection = db.getCollection("StudyPool");
  }

  /** Convenience constructor using DBAccess. */
  public InvitationDAO() {
    this(DBAccess.getDatabase());
  }

  // ====================================================================
  //  Filter use case methods
  // ====================================================================

  @Override
  public List<Invitation> findAll() {
    List<Invitation> list = new ArrayList<>();
    for (Document doc : studyPoolCollection.find()) {
      list.add(DBInvitationMapper.fromDocument(doc));
    }
    return list;
  }

  public List<Invitation> findByCourse(String course) {
    List<Invitation> list = new ArrayList<>();
    for (Document doc : studyPoolCollection.find(eq("course", course))) {
      list.add(DBInvitationMapper.fromDocument(doc));
    }
    return list;
  }

  public Invitation findById(String id) {
    Document doc = studyPoolCollection
      .find(eq("_id", new ObjectId(id)))
      .first();
    if (doc == null) {
      return null;
    }
    return DBInvitationMapper.fromDocument(doc);
  }

  // ====================================================================
  //  Cancel use case methods
  // ====================================================================

  @Override
  public Document findInvitationByOwner(String ownerName) {
    return studyPoolCollection.find(eq("owner", ownerName)).first();
  }

  @Override
  public Iterable<Document> findInvitationsByParticipant(String username) {
    return studyPoolCollection.find(eq("participants", username));
  }
  @Override
  public void removeParticipantFromInvitation(Document doc, String username) {
    ObjectId id = doc.getObjectId("_id");
    studyPoolCollection.updateOne(eq("_id", id),
      pull("participants", username));
  }

  @Override
  public void deleteInvitation(Document doc) {
    ObjectId id = doc.getObjectId("_id");
    studyPoolCollection.deleteOne(eq("_id", id));
  }

  // ====================================================================
  //  Accept use case methods
  // ====================================================================

  @Override
  public void addParticipantToInvitation(Document doc, String username) {
    ObjectId id = doc.getObjectId("_id");
    studyPoolCollection.updateOne(eq("_id", id),
      addToSet("participants", username));
  }

  @Override
  public Document findInvitationById(String invitationId) {
    ObjectId id = new ObjectId(invitationId);
    return studyPoolCollection.find(eq("_id", id)).first();
  }
  @Override
  public Document findInvitationByParticipant(String username) {
    return studyPoolCollection.find(eq("participants", username)).first();
  }

  // ====================================================================
  //  Make invitation use case methods
  // ====================================================================

  /** Map Invitation -> MongoDB Document. */
  private Document toDocument(Invitation inv) {
    Document doc = new Document();
    doc.append("course", inv.getCourse());
    doc.append("description", inv.getDescription());
    doc.append("date", inv.getDate().toString());      // "2025-11-26"
    doc.append("startTime", inv.getStartTime().toString()); // "15:00"
    doc.append("endTime", inv.getEndTime().toString());
    doc.append("mode", inv.getMode());
    doc.append("location", inv.getLocation());
    doc.append("capacity", inv.getCapacity());
    doc.append("owner", inv.getOwner().getEmail());

    // participants as list of emails
    List<String> participantNames = new ArrayList<>();
    for (User u : inv.getParticipants()) {
      participantNames.add(u.getEmail());
    }
    doc.append("participants", participantNames);

    return doc;
  }

  @Override
  public void save(Invitation inv) {
    studyPoolCollection.insertOne(toDocument(inv));
  }

  @Override
  public boolean existsOverlap(String course, LocalDate date,
                               LocalTime start, LocalTime end) {

    // same course + same date
    List<Document> docs = studyPoolCollection.find(
      Filters.and(
        Filters.eq("course", course),
        Filters.eq("date", date.toString())
      )
    ).into(new ArrayList<>());

    for (Document d : docs) {
      LocalTime otherStart = LocalTime.parse(d.getString("startTime"));
      LocalTime otherEnd   = LocalTime.parse(d.getString("endTime"));

      boolean overlap = start.isBefore(otherEnd)
        && otherStart.isBefore(end);

      if (overlap) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean ownerHasOverlap(User owner, LocalDate date,
                                 LocalTime start, LocalTime end) {

    List<Document> docs = studyPoolCollection.find(
      Filters.and(
        Filters.eq("owner", owner.getEmail()),
        Filters.eq("date", date.toString())
      )
    ).into(new ArrayList<>());

    for (Document d : docs) {
      LocalTime otherStart = LocalTime.parse(d.getString("startTime"));
      LocalTime otherEnd   = LocalTime.parse(d.getString("endTime"));

      boolean overlap = start.isBefore(otherEnd)
        && otherStart.isBefore(end);

      if (overlap) {
        return true;
      }
    }
    return false;
  }

  @Override
  public List<Invitation> listByCourseAndDate(String course, LocalDate date) {
    List<Invitation> result = new ArrayList<>();
    for (Document d : studyPoolCollection.find(
      Filters.and(
        Filters.eq("course", course),
        Filters.eq("date", date.toString())
      ))) {
      result.add(DBInvitationMapper.fromDocument(d));
    }
    return result;
  }
}
//package data_access;
//
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.Filters;
//import entity.Invitation;
//import entity.User;
//import mapper.DBInvitationMapper;
//import org.bson.Document;
//import org.bson.types.ObjectId;
//import use_case.accept.AcceptInvitationUserDataAccessInterface;
//import use_case.cancel.CancelInvitationDataAccessInterface;
//import use_case.filter.FilterInvitationDataAccessInterface;
//import use_case.make_invitation.MakeInvitationDataAccessInterface;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//import static com.mongodb.client.model.Filters.eq;
//import static com.mongodb.client.model.Updates.addToSet;
//import static com.mongodb.client.model.Updates.pull;
//
//public class InvitationDAO implements FilterInvitationDataAccessInterface,
//                                      CancelInvitationDataAccessInterface,
//                                      AcceptInvitationUserDataAccessInterface,
//                                      MakeInvitationDataAccessInterface {
//
//    private final MongoCollection<Document> collection;
//
//    public InvitationDAO(MongoDatabase db) {
//        this.collection = db.getCollection("StudyPool");
//    }
//
//    public List<Invitation> findAll() {
//        List<Invitation> list = new ArrayList<>();
//        for (Document doc : collection.find()) {
//            list.add(DBInvitationMapper.fromDocument(doc));
//        }
//        return list;
//    }
//
////can delete useless (just there bc i generated them as filteration examples
//    public Invitation findById(String id) {
//        Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
//        if (doc == null) return null;
//        return DBInvitationMapper.fromDocument(doc);
//    }
//
//    public List<Invitation> findByCourse(String course) {
//        List<Invitation> list = new ArrayList<>();
//        for (Document doc : collection.find(Filters.eq("course", course))) {
//            list.add(DBInvitationMapper.fromDocument(doc));
//        }
//        return list;
//    }
//
//  @Override
//  public Document findInvitationById(String invitationId) {
//    ObjectId id = new ObjectId(invitationId);
//    return studyPoolCollection.find(eq("_id", id)).first();
//  }
//
//  // optional: find invitation by owner (parallel to cancel DAO)
//  @Override
//  public Document findInvitationByOwner(String ownerName) {
//    return studyPoolCollection.find(eq("owner", ownerName)).first();
//  }
//
//  // all invitations where this user is already a participant
//  @Override
//  public Iterable<Document> findInvitationsByParticipant(String username) {
//    return studyPoolCollection.find(eq("participants", username));
//  }
//
//  // add user to participants array (no duplicates)
//  @Override
//  public void addParticipantToInvitation(Document doc, String username) {
//    ObjectId id = doc.getObjectId("_id");
//    studyPoolCollection.updateOne(eq("_id", id), addToSet("participants", username));
//  }
//
//  @Override
//  public Document findInvitationByParticipant(String username) {
//    return studyPoolCollection.find(eq("participants", username)).first();
//  }
//
//  @Override
//  public Document findInvitationByOwner(String ownerName) {
//    return studyPoolCollection.find(eq("owner", ownerName)).first();
//  }
//
//  @Override
//  public void removeParticipantFromInvitation(Document doc, String username) {
//    ObjectId id = doc.getObjectId("_id");
//    studyPoolCollection.updateOne(eq("_id", id), pull("participants", username));
//  }
//
//  @Override
//  public void deleteInvitation(Document doc) {
//    ObjectId id = doc.getObjectId("_id");
//    studyPoolCollection.deleteOne(eq("_id", id));
//  }
//
//  private Document toDocument(Invitation inv) {
//    Document doc = new Document();
//    doc.append("course", inv.getCourse());
//    doc.append("description", inv.getDescription());
//    doc.append("date", inv.getDate().toString());          // store as ISO string "2025-11-26"
//    doc.append("startTime", inv.getStartTime().toString()); // "15:00"
//    doc.append("endTime", inv.getEndTime().toString());
//    doc.append("mode", inv.getMode());
//    doc.append("location", inv.getLocation());
//    doc.append("capacity", inv.getCapacity());
//    doc.append("owner", inv.getOwner().getEmail());          // you can later also store ownerId
//    // participants as list of usernames:
//    List<String> participantNames = new ArrayList<>();
//    for (User u : inv.getParticipants()) {
//      participantNames.add(u.getEmail());
//    }
//    doc.append("participants", participantNames);
//
//    return doc;
//  }
//
//  private Invitation fromDocument(Document doc) {
//    // You can implement this later if you need to read invitations back.
//    // For now, not strictly necessary for MakeInvitation use case.
//    throw new UnsupportedOperationException("fromDocument not implemented yet");
//  }
//
//  // endregion
//
//  // region MakeInvitationDataAccessInterface methods ------------------------
//
//  @Override
//  public void save(Invitation inv) {
//    invitations.insertOne(toDocument(inv));
//  }
//
//  @Override
//  public boolean existsOverlap(String course, LocalDate date,
//                               LocalTime start, LocalTime end) {
//
//    // Find same course + date first
//    List<Document> docs = invitations.find(
//      Filters.and(
//        Filters.eq("course", course),
//        Filters.eq("date", date.toString())
//      )
//    ).into(new ArrayList<>());
//
//    for (Document doc : docs) {
//      LocalTime otherStart = LocalTime.parse(doc.getString("startTime"));
//      LocalTime otherEnd   = LocalTime.parse(doc.getString("endTime"));
//
//      boolean overlap =
//        start.isBefore(otherEnd) && otherStart.isBefore(end);
//
//      if (overlap) return true;
//    }
//    return false;
//  }
//
//  @Override
//  public boolean ownerHasOverlap(User owner, LocalDate date,
//                                 LocalTime start, LocalTime end) {
//
//    List<Document> docs = invitations.find(
//      Filters.and(
//        Filters.eq("owner", owner.getEmail()),
//        Filters.eq("date", date.toString())
//      )
//    ).into(new ArrayList<>());
//
//    for (Document doc : docs) {
//      LocalTime otherStart = LocalTime.parse(doc.getString("startTime"));
//      LocalTime otherEnd   = LocalTime.parse(doc.getString("endTime"));
//
//      boolean overlap =
//        start.isBefore(otherEnd) && otherStart.isBefore(end);
//
//      if (overlap) return true;
//    }
//    return false;
//  }
//
//  @Override
//  public List<Invitation> listByCourseAndDate(String course, LocalDate date) {
//    // Optional for your use case; can implement later if needed.
//    return List.of();
//  }
//}
