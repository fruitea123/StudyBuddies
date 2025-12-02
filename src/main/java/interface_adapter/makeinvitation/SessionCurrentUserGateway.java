package interface_adapter.makeinvitation;

import entity.User;
import use_case.makeinvitation.CurrentUserGateway;

/**
 * a gateway to catch the current user.
 */
public class SessionCurrentUserGateway implements CurrentUserGateway {
  private User currentUser;

  /**
   * getter for current user.
   *
   * @return user
   */
  @Override
  public User getCurrentUser() {
    return currentUser;
  }

  /**
   * setter for current user.
   *
   * @param u user
   */
  public void setCurrentUser(User u) {
    this.currentUser = u;
  }
}
