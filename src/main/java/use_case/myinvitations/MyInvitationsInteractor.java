package use_case.myinvitations;

import data_access.InvitationDAO;
import entity.Invitation;
import entity.User;
import java.util.ArrayList;
import java.util.List;

public class MyInvitationsInteractor implements MyInvitationsInputBoundary {

    private final InvitationDAO dao;
    private final MyInvitationsOutputBoundary presenter;

    public MyInvitationsInteractor(InvitationDAO dao,
                                   MyInvitationsOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    public List<Invitation> getStudyPool() {
        return dao.findAll();
    }

    public List<Invitation> FilterByOwned(String userEmail) {
        List<Invitation> result = new ArrayList<>();
        for (Invitation invitation : dao.findAll()) {
            if (invitation.getOwner().getEmail().equals(userEmail)) {
                result.add(invitation);
            }
        }
        return result;
    }

    public List<Invitation> FilterByParticipant(String userEmail) {
        List<Invitation> result = new ArrayList<>();
        for (Invitation invitation : dao.findAll()) {
            for (User participant : invitation.getParticipants()) {
                if (participant.getEmail().equals(userEmail)) {
                    result.add(invitation);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void loadMyInvitations(String userEmail) {
        List<Invitation> owned = FilterByOwned(userEmail);
        List<Invitation> participating = FilterByParticipant(userEmail);

        MyInvitationsOutputData outputData = new MyInvitationsOutputData(owned, participating);
        presenter.presentMyInvitations(outputData);
    }
}
