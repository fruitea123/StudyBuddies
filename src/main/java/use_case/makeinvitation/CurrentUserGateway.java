package use_case.makeinvitation;

import entity.User;

/**
 * Gateway that provides the currently logged-in user
 * to the make invitation use case -> set current user as owner of new invitation.
 */
public interface CurrentUserGateway {

  /**
   * Returns the current logged-in user, or {@code null} if nobody is logged in.
   *
   * @return the current user, or null if no user is logged in
   */
  User getCurrentUser();
}
