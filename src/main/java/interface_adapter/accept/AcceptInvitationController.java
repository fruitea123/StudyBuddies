package interface_adapter.accept;

import use_case.accept.AcceptInvitationInputBoundary;
import use_case.accept.AcceptInvitationInputData;

/**
 * Controller for the Accept Invitation use case.
 *
 * <p>Translates UI input (invitation id and username) into an
 * {@link AcceptInvitationInputData} object and delegates the
 * request to the input boundary.
*/
public class AcceptInvitationController {
  private final AcceptInvitationInputBoundary inputBoundary;
  /**
     * Constructs a controller with the given accept‑invitation input boundary.
     *
     * @param inputBoundary use case interactor that will handle accept requests.
  */

  public AcceptInvitationController(AcceptInvitationInputBoundary inputBoundary) {
    this.inputBoundary = inputBoundary;
  }
  /**
     * Handles a request from the UI to accept an invitation.
     *
     * <p>Wraps the raw parameters in an {@link AcceptInvitationInputData}
     * object and passes it to the use case interactor.
     *
     * @param invitationId identifier of the invitation to be accepted.
     * @param username     user who is accepting the invitation.
  */

  public void acceptInvitation(String invitationId, String username) {
    AcceptInvitationInputData inputData = new AcceptInvitationInputData(username, invitationId);
    inputBoundary.acceptInvitation(inputData);
  }
}
