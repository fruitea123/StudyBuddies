package interface_adapter.makeinvitation;

import use_case.makeinvitation.MakeInvitationOutputBoundary;
import use_case.makeinvitation.MakeInvitationOutputData;

/**
 * presenter with the output message from make invitation.
 */
public class MakeInvitationPresenter implements MakeInvitationOutputBoundary {

  private final MakeInvitationViewModel vm;

  /**
   * presenter to the update the result.
   *
   * @param vm view model for make invitation
   */
  public MakeInvitationPresenter(MakeInvitationViewModel vm) {
    this.vm = vm;
  }

  /**
   * method to present success message.
   *
   * @param out message and invitation
   */
  @Override
  public void presentSuccess(MakeInvitationOutputData out) {
    MakeInvitationState s = vm.getState();
    s.setSuccessMessage(out.getMessage());
    s.setErrorMessage("");
    // notify view to update
    vm.firePropertyChange();
  }

  /**
   * message to present failure message.
   *
   * @param errorMessage message
   */
  @Override
  public void presentFailure(String errorMessage) {
    MakeInvitationState s = vm.getState();
    s.setSuccessMessage("");
    s.setErrorMessage(errorMessage);
    vm.firePropertyChange();
  }
}
