package use_case.makeinvitation;

/**
 * interface for make invitation output.
 */
public interface MakeInvitationOutputBoundary {

  /**
   * method to present success message.
   *
   * @param response message and invitation
   */
  void presentSuccess(MakeInvitationOutputData response);

  /**
   * method to present error message.
   *
   * @param errorMessage message
   */
  void presentFailure(String errorMessage);
}