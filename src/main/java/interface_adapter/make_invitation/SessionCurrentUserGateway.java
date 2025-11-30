package interface_adapter.make_invitation;

import entity.User;
import use_case.make_invitation.CurrentUserGateway;

public class SessionCurrentUserGateway implements CurrentUserGateway {
    private User currentUser;

    public void setCurrentUser(User u) {
        this.currentUser = u;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}
