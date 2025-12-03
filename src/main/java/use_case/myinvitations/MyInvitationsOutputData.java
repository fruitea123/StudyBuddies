package use_case.myinvitations;

import entity.Invitation;
import java.util.List;

public class MyInvitationsOutputData {

    private final List<Invitation> owned;
    private final List<Invitation> participating;

    public MyInvitationsOutputData(List<Invitation> owned,
                                   List<Invitation> participating) {
        this.owned = owned;
        this.participating = participating;
    }

    public List<Invitation> getOwned() {
        return owned;
    }

    public List<Invitation> getParticipating() {
        return participating;
    }
}
