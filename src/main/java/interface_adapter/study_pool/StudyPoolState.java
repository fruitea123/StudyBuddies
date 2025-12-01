package interface_adapter.study_pool;

import entity.Invitation;

import java.util.List;

public class StudyPoolState {
    private List<Invitation> invitations;

    public List<Invitation> getInvitations() {
        return invitations;
    }
    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }
}
