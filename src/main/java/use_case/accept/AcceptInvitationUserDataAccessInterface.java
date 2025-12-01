package use_case.accept;

import org.bson.Document;

public interface AcceptInvitationUserDataAccessInterface {

    // Find the invitation the user wants to accept (by id or owner, depending on your design)
    Document findInvitationById(String invitationId);

    Document findInvitationByOwner(String ownerName);

    // Find all invitations where this user is already a participant (for time‑conflict checks)
    Iterable<Document> findInvitationsByParticipant(String username);

    // Add the user to the participants list of the given invitation
    void addParticipantToInvitation(Document invitation, String username);
}
