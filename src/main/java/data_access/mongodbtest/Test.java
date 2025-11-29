package data_access.mongodbtest;

import com.mongodb.client.MongoDatabase;
import data_access.DBAccess;
import data_access.InvitationDAO;
import entity.Invitation;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        testFindAll();
    }
    public static void testFindAll() {
        DBAccess dbAccess = new DBAccess();
        MongoDatabase db = dbAccess.getDatabase();

        InvitationDAO dao = new InvitationDAO(db);

        List<Invitation> list = dao.findAll();

        list.forEach(System.out::println);
    }
}
