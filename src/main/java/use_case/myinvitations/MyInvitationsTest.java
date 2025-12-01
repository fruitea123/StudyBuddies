package use_case.myinvitations;

import com.mongodb.client.MongoDatabase;
import data_access.DBAccess;
import data_access.InvitationDAO;
import data_access.mongodbtest.Test;
import entity.Invitation;

import java.util.List;


public class MyInvitationsTest {

    private static String user = "Tim";
    private static DBAccess dbAccess = new DBAccess();
    private static MongoDatabase db = dbAccess.getDatabase();
    private static InvitationDAO invitationDAO = new InvitationDAO(db);

    public static void main(String[] args) {
        MyInvitations myInvitations = new MyInvitations(invitationDAO);
        System.out.println(myInvitations.FilterByOwned(user));
        System.out.println(myInvitations.FilterByParticipant(user));
    }

}
