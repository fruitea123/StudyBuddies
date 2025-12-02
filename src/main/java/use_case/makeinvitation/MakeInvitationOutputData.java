package use_case.makeinvitation;

import entity.Invitation;

/**
 * class for invitation output data.
 */
public class MakeInvitationOutputData {
  private final Invitation invitation;
  private final String message; // message to UI

  /**
   * create a new invitation output data with given parameters.
   *
   * @param invitation new created invitation
   * @param message message
   */
  public MakeInvitationOutputData(Invitation invitation, String message) {
    this.invitation = invitation;
    this.message = message;
  }

  /**
   * getter for invitation.
   *
   * @return invitation
   */
  public Invitation getInvitation() {
    return invitation;
  }

  /**
   * getter for message.
   *
   * @return message
   */
  public String getMessage() {
    return message;
  }
}