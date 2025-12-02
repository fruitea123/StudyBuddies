package interface_adapter.myinvitations;

import java.util.Collections;
import java.util.List;

/**
 * ViewModel for the entire "My Invitations" screen:
 * - Owned invitations
 * - Participating invitations
 */
public class MyInvitationsViewModel {

    private List<InvitationItemViewModel> ownedInvitations;
    private List<InvitationItemViewModel> participatingInvitations;

    public List<InvitationItemViewModel> getOwnedInvitations() {
        return ownedInvitations == null
                ? Collections.emptyList()
                : Collections.unmodifiableList(ownedInvitations);
    }

    public void setOwnedInvitations(List<InvitationItemViewModel> ownedInvitations) {
        this.ownedInvitations = ownedInvitations;
    }

    public List<InvitationItemViewModel> getParticipatingInvitations() {
        return participatingInvitations == null
                ? Collections.emptyList()
                : Collections.unmodifiableList(participatingInvitations);
    }

    public void setParticipatingInvitations(List<InvitationItemViewModel> participatingInvitations) {
        this.participatingInvitations = participatingInvitations;
    }
}
