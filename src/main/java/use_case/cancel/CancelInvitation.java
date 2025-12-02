package use_case.cancel;

import org.bson.Document;

/**
 * A class for canceling or leaving invitations.
 */
public class CancelInvitation {

  private final CancelInvitationDataAccessInterface dataAccess;
  private String username;
  private String ownerName;

  /**
     * Initializes a CancelInvitation object with the given data access interface.
     *
     * @param dataAccess The data access interface used to interact with invitations.
  */
  public CancelInvitation(CancelInvitationDataAccessInterface dataAccess) {
    this.dataAccess = dataAccess;
  }

  /**
     * Sets the username for the participant leaving an invitation.
     *
     * @param username The username of the participant.
  */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
     * Sets the owner name for the invitation to be deleted.
     *
     * @param ownerName The username of the owner.
  */
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  /**
     * Allows the participant to leave an invitation.
  */
  public void leave() {
    Document invitation = dataAccess.findInvitationByParticipant(username);

    if (invitation == null) {
      System.out.println("No session found for user: " + username);
      return;
    }

    dataAccess.removeParticipantFromInvitation(invitation, username);

    System.out.println(
                "User " + username + " left session with ID: " + invitation.getObjectId("_id")
    );
  }

  /**
     * Allows the owner to delete their invitation.
  */
  public void delete() {
    Document invitation = dataAccess.findInvitationByOwner(ownerName);

    if (invitation == null) {
      System.out.println("No session found for owner: " + ownerName);
      return;
    }

    dataAccess.deleteInvitation(invitation);

    System.out.println(
         "Owner " + ownerName + " deleted session with ID: " + invitation.getObjectId("_id"));
  }
}
