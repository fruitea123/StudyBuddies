package use_case.cancel;

import data_access.MongoCancelInvitationDAO;

public class CancelInvitationTest {

    public static void main(String[] args) {

        MongoCancelInvitationDAO dao = new MongoCancelInvitationDAO();
        CancelInvitation cancel = new CancelInvitation(dao);

        cancel.setOwnerName("john");
        cancel.delete();

        cancel.setUsername("max");
        cancel.leave();

        System.out.println("Test complete.");
    }
}
