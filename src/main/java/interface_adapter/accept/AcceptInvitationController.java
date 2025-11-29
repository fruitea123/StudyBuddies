package interface_adapter.accept;

import use_case.accept.AcceptInvitationInputBoundary;
import use_case.accept.AcceptInvitationInputData;

public class AcceptInvitationController {
    private final AcceptInvitationInputBoundary inputBoundary;

    public AcceptInvitationController(AcceptInvitationInputBoundary acceptInvitationInputBoundary) {
        this.inputBoundary = acceptInvitationInputBoundary;
    }

    public void acceptInvitation(String invitationId, String username) {
        AcceptInvitationInputData inputData = new AcceptInvitationInputData(username, invitationId);
        inputBoundary.acceptInvitation(inputData);
    }
}
