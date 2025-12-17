package use_case.accept;

import java.util.Date;
import org.bson.Document;

/**
 * A class for accepting invitations.
 */
public class AcceptInvitationInteractor implements AcceptInvitationInputBoundary {
  private final AcceptInvitationOutputBoundary outputBoundary;
  private final AcceptInvitationDataAccessInterface dataAccess;

  /**
     * Interactor depends on a data access interface instead of Mongo directly.
  */
  public AcceptInvitationInteractor(AcceptInvitationOutputBoundary outputBoundary,
                                      AcceptInvitationDataAccessInterface dataAccess) {
    this.outputBoundary = outputBoundary;
    this.dataAccess = dataAccess;
  }

  @Override
  public void acceptInvitation(AcceptInvitationInputData inputData) {
    String username = inputData.getUsername();
    String ownername = inputData.getOwnerName();



    // 2. Find target session (by invitationId here; you could also use owner name)
    Document session = dataAccess.findInvitationByOwner(ownername);
    if (session == null) {
      outputBoundary.prepareFailureView("No session found for owner: " + ownername);
      return;
    }

    // 1. Check for time conflicts
    if (hasTimeConflict(username, session)) {
        outputBoundary.prepareFailureView("time conflicts");
          return;
      }
    // 3. Add user to participants
    dataAccess.addParticipantToInvitation(session, username);
    outputBoundary.prepareSuccessView("Invitation Accepted");
  }

  /**
     * Checks if there is any time overlap between:
     * - the target session (owner = sessionOwner)
     * - any sessions the user is already participating in.
  */
  boolean hasTimeConflict(String username, Document targetSession) {

    Date targetStart = targetSession.getDate("startTime");
    Date targetEnd = targetSession.getDate("endTime");
    if (targetStart == null || targetEnd == null) {
      return false;
    }

    // all sessions user is in
    for (Document userSession : dataAccess.findInvitationsByParticipant(username)) {
      Date userStart = userSession.getDate("startTime");
      Date userEnd = userSession.getDate("endTime");
      if (userStart == null || userEnd == null) {
        continue;
      }

      if (targetStart.before(userEnd) && userStart.before(targetEnd)) {
        return true;
      }
    }
    return false;
  }
}
