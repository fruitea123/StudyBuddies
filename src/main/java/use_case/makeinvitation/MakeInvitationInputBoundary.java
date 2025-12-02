package use_case.makeinvitation;

/**
 * interface for make invitation input.
 */

public interface MakeInvitationInputBoundary {

  /**
   * execute method implemented in make invitation interactor.
   *
   * @param request packed input data
   */
  void execute(MakeInvitationInputData request);
}