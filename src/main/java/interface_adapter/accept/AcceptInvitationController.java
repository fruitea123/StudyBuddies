package interface_adapter.accept;

import entity.User;
import use_case.accept.AcceptInvitationInputBoundary;
import use_case.accept.AcceptInvitationInputData;

public class AcceptInvitationController {
    private final AcceptInvitationInputBoundary inputBoundary;

    public AcceptInvitationController(AcceptInvitationInputBoundary acceptInvitationInputBoundary) {
        this.inputBoundary = acceptInvitationInputBoundary;
    }

    public void acceptInvitation(String ownerName, String username) {
        AcceptInvitationInputData inputData = new AcceptInvitationInputData(ownerName, username);
        inputBoundary.acceptInvitation(inputData);
    }
}
