package use_case.cancel;

import org.bson.Document;

public class CancelInvitation {

    private final CancelInvitationDataAccessInterface dataAccess;
    private String username;
    private String ownerName;

    public CancelInvitation(CancelInvitationDataAccessInterface dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void leave() {
        Document invitation = dataAccess.findInvitationByParticipant(username);

        if (invitation == null) {
            System.out.println("No session found for user: " + username);
            return;
        }

        dataAccess.removeParticipantFromInvitation(invitation, username);

        System.out.println("User " + username +
                " left session with ID: " + invitation.getObjectId("_id"));
    }

    public void delete() {
        Document invitation = dataAccess.findInvitationByOwner(ownerName);

        if (invitation == null) {
            System.out.println("No session found for owner: " + ownerName);
            return;
        }

        dataAccess.deleteInvitation(invitation);

        System.out.println("Owner " + ownerName +
                " deleted session with ID: " + invitation.getObjectId("_id"));
    }
}
