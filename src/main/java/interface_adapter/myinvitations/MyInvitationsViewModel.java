package interface_adapter.myinvitations;

import interface_adapter.ViewModel;
import view.forms.MyInvitationsView;

import java.util.Collections;
import java.util.List;

/**
 * ViewModel for the entire "My Invitations" screen:
 * - Owned invitations
 * - Participating invitations
 */
public class MyInvitationsViewModel extends ViewModel<MyInvitationsState> {

    private List<InvitationItemViewModel> ownedInvitations;
    private List<InvitationItemViewModel> participatingInvitations;

    public MyInvitationsViewModel() {
        super(MyInvitationsView.VIEW_NAME);
        this.setState(new MyInvitationsState());
    }

    public List<InvitationItemViewModel> getOwnedInvitations() {
        return ownedInvitations == null
                ? Collections.emptyList()
                : Collections.unmodifiableList(ownedInvitations);
    }

    public void setOwnedInvitations(java.util.List<InvitationItemViewModel> list) {
        this.getState().ownedInvitations = list;
        firePropertyChange();
    }

    public List<InvitationItemViewModel> getParticipatingInvitations() {
        return participatingInvitations == null
                ? Collections.emptyList()
                : Collections.unmodifiableList(participatingInvitations);
    }

    public void setParticipatingInvitations(java.util.List<InvitationItemViewModel> list) {
        this.getState().participatingInvitations = list;
        firePropertyChange();
    }
}
