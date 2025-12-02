package interface_adapter.myinvitations;

/**
 * ViewModel for a single invitation card in the "My Invitations" screen.
 * This is what the view uses to populate DeleteInvitationCard / LeaveInvitationCard.
 */
public class InvitationItemViewModel {

    private String invitationId;
    private String title;
    private String description;
    private boolean owned; // true = owned by current user, false = just participating

    public InvitationItemViewModel() {
    }

    public InvitationItemViewModel(String invitationId, String title, String description, boolean owned) {
        this.invitationId = invitationId;
        this.title = title;
        this.description = description;
        this.owned = owned;
    }

    public String getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(String invitationId) {
        this.invitationId = invitationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }
}
