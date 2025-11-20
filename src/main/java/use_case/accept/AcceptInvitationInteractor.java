package use_case.accept;

import entity.Invitation;
import entity.InvitationBuilder;

// application/AcceptInvitationInteractor.java
public class AcceptInvitationInteractor {
    private final AcceptInvitationUserDataAccessInterface dataAccess;

    public AcceptInvitationInteractor(AcceptInvitationUserDataAccessInterface dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AcceptInvitationOutputData execute(AcceptInvitationInputData inputData) {
        // 1. Check timing conflict
        Invitation invitation = dataAccess.fetchInvitationById(inputData.getInvitationId());
        if (dataAccess.hasTimingConflict(inputData.getUsername(), invitation.getStartTime(), invitation.getEndTime())) {
            return new AcceptInvitationOutputData(false, "conflict with timings");
        }
        boolean addResult = dataAccess.addUserToInvitation(
                inputData.getUsername(), inputData.getInvitationId(), invitation);
        if (addResult) {
            return new AcceptInvitationOutputData(true, "Invitation accepted!");
        } else {
            return new AcceptInvitationOutputData(false, "Failed to accept invitation.");
        }
    }
}
