package usecase.cancel;

import data_access.MongoCancelInvitationDAO;

//test
public class CancelInvitationTest {//cancel invitation test
    public static void main(String[] args) {

        MongoCancelInvitationDAO dao = new MongoCancelInvitationDAO();
        CancelInvitation cancel = new CancelInvitation(dao);

        cancel.setOwnerName("Tim@mail.utoronto.ca");
        cancel.delete();

        cancel.setOwnerName("");
        cancel.delete();

        cancel.setUsername("");
        cancel.leave();

        cancel.setUsername("max@mail.utoronto.ca");
        cancel.leave();

        System.out.println("Test complete.");
    }
}
