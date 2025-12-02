package use_case.cancel;

import org.bson.Document;

/**
 * Interface for accessing invitation data in the CancelInvitation system.
 */
public interface CancelInvitationDataAccessInterface {

  /**
     * Finds the invitation for a given participant.
     *
     * @param username The username of the participant.
     * @return The invitation Document, or null if not found.
  */
  Document findInvitationByParticipant(String username);

  /**
     * Finds the invitation owned by the given owner.
     *
     * @param ownerName The owner's username.
     * @return The invitation Document, or null if not found.
  */
  Document findInvitationByOwner(String ownerName);

  /**
     * Removes a participant from an invitation.
     *
     * @param invitation The invitation document.
     * @param username The participant to remove.
  */
  void removeParticipantFromInvitation(Document invitation, String username);

  /**
     * Deletes an invitation.
     *
     * @param invitation The invitation document to delete.
  */
  void deleteInvitation(Document invitation);
}
