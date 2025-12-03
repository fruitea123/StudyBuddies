//package use_case.myinvitations;
//
//import com.mongodb.client.MongoDatabase;
//import data_access.DBAccess;
//import data_access.InvitationDAO;
////import data_access.mongodbtest.Test;
//
//
//public class MyInvitationsTest {
//
//    private static String user = "Tim";
//    private static DBAccess dbAccess = new DBAccess();
//    private static MongoDatabase db = dbAccess.getDatabase();
//    private static InvitationDAO invitationDAO = new InvitationDAO(db);
//
//    public static void main(String[] args) {
//        MyInvitationsInteractor myInvitations = new MyInvitationsInteractor(invitationDAO);
//        System.out.println(myInvitations.FilterByOwned(user));
//        System.out.println(myInvitations.FilterByParticipant(user));
//    }
//
//}


// OLD OBSOLETE TEST