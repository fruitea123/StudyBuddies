package data_access;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import entity.Invitation;
import mapper.DBInvitationMapper;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class InvitationDAO {

    private final MongoCollection<Document> collection;

    public InvitationDAO(MongoDatabase db) {
        this.collection = db.getCollection("StudyPool");
    }

    public List<Invitation> findAll() {
        List<Invitation> list = new ArrayList<>();
        for (Document doc : collection.find()) {
            list.add(DBInvitationMapper.fromDocument(doc));
        }
        return list;
    }
//can delete useless (just there bc i generated them as filteration examples
    public Invitation findById(String id) {
        Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
        if (doc == null) return null;
        return DBInvitationMapper.fromDocument(doc);
    }

    public List<Invitation> findByCourse(String course) {
        List<Invitation> list = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("course", course))) {
            list.add(DBInvitationMapper.fromDocument(doc));
        }
        return list;
    }
}
