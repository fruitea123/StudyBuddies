package use_case.myinvitations;

import com.mongodb.client.MongoDatabase;
import data_access.DBAccess;
import data_access.InvitationDAO;
import entity.Invitation;
import entity.InvitationBuilder;

import java.util.ArrayList;
import java.util.List;

public class MyInvitations {
    private final InvitationDAO dao;

    public MyInvitations(InvitationDAO dao) {
        this.dao = dao;
    }

    public List<Invitation> getStudyPool() {
        return dao.findAll();
    }

    public List<Invitation> FilterByOwned(String user) {
        List<Invitation> result = new ArrayList<>();
        List<Invitation> invitations = dao.findAll();
        for  (Invitation invitation : invitations) {
            if (InvitationBuilder.getOwner().equals(user)) {
                result.add(invitation);
            }
        }
        return result;
    }

    public List<Invitation> FilterByParticipant(String user) {
        List<Invitation> result = new ArrayList<>();
        List<Invitation> invitations = dao.findAll();
        for  (Invitation invitation : invitations) {
            List<String> participants = InvitationBuilder.getParticipants(); //User to string resolution
            for (String participant : participants) {
                if (participant.equals(user)) {
                    result.add(invitation);
                    break;
                }
            }
        }
        return result;
    }
}
