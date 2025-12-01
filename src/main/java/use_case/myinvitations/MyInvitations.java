package use_case.myinvitations;

import com.mongodb.client.MongoDatabase;
import data_access.DBAccess;
import data_access.InvitationDAO;
import entity.Invitation;
import entity.User;

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

    public List<Invitation> FilterByOwned(String userEmail) {
        List<Invitation> result = new ArrayList<>();
        List<Invitation> invitations = dao.findAll();
        for  (Invitation invitation : invitations) {
            if (invitation.getOwner().getEmail().equals(userEmail)) {
                result.add(invitation);
            }
        }
        return result;
    }

    public List<Invitation> FilterByParticipant(String userEmail) {
        List<Invitation> result = new ArrayList<>();
        List<Invitation> invitations = dao.findAll();
        for  (Invitation invitation : invitations) {
            List<User> participants = invitation.getParticipants();
            for (User participant : participants) {
                if (participant.getEmail().equals(userEmail)) {
                    result.add(invitation);
                    break;
                }
            }
        }
        return result;
    }
}
