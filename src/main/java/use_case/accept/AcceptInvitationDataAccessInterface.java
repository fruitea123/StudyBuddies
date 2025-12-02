package use_case.accept;

import org.bson.Document;

/**
 * Interface for accessing invitation data in the AcceptInvitation system.
 */
public interface AcceptInvitationDataAccessInterface {

  /**
     * Finds the invitation for a given participant.
     *
     * @param invitationId The id of the participant.
     * @return The invitation Document, or null if not found.
  */
  // Find the invitation the user wants to accept (by id or owner, depending on your design)
  Document findInvitationById(String invitationId);

  /**
     * Finds the invitation owned by the given owner.
     *
     * @param ownerName The owner's username.
     * @return The invitation Document, or null if not found.
  */
  // Find the invitation the user wants to accept (by id or owner, depending on your design)
  Document findInvitationByOwner(String ownerName);

  /**
     * Finds the list of invitation user has accepted.
     *
     * @param username The user's username
     * @return The invitation Document, or null if not found.
  */
  // Find all invitations where this user is already a participant (for time‑conflict checks)
  Iterable<Document> findInvitationsByParticipant(String username);

  /**
     * Adda a participant to an invitation.
     *
     * @param invitation The invitation document.
     * @param username The participant to remove.
  */
  // Add the user to the participants list of the given invitation
  void addParticipantToInvitation(Document invitation, String username);
}
