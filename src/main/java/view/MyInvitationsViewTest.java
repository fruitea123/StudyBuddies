package usecase.myinvitations;

import com.mongodb.client.MongoDatabase;
import data_access.DBAccess;
import data_access.InvitationDAO;
import view.forms.MyInvitationsView;

public class MyInvitationsViewTest {

    public static void main(String[] args) {

        String user = "Tim";   // same testing user
        DBAccess dbAccess = new DBAccess();
        MongoDatabase db = dbAccess.getDatabase();

        InvitationDAO invitationDAO = new InvitationDAO(db);

        /*
        MyInvitations interactor = new MyInvitations(invitationDAO);

        List<Invitation> invitations = interactor.FilterByOwned(user);
        invitations.addAll(interactor.FilterByParticipant(user));

        MyInvitationsView view = new MyInvitationsView();

        view.loadInvitations(invitations, user); */

        MyInvitationsView view = new MyInvitationsView();

        view.loadInvitations(invitationDAO, user);

        view.setVisible(true);
    }
}
