package use_case.accept;

import entity.Invitation;
import entity.User;

public interface AcceptInvitationUserDataAccessInterface {

        Invitation fetchInvitationById(String invitationId);
        User fetchUserByUsername(String username);
        boolean hasTimingConflict(String username, String startTime, String endTime);
        boolean addUserToInvitation(String username, String invitationId, Invitation summary);

}
