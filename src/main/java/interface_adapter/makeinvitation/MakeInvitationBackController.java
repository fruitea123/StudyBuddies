package interface_adapter.makeinvitation;

import interface_adapter.ViewManagerModel;
import interface_adapter.myinvitations.MyInvitationsViewModel;


/**
 * Controller for switching back to the profile view after invitation successfully created.
 */
public class MakeInvitationBackController {

  private final ViewManagerModel viewManagerModel;
  private final MakeInvitationViewModel makeInvitationViewModel;
  private final String myInvitationsViewName;


  /**
   * controller for switching back.
   *
   */
  public MakeInvitationBackController(ViewManagerModel viewManagerModel,
                                      MakeInvitationViewModel makeInvitationViewModel,
                                      String myInvitationsViewName) {
    this.viewManagerModel = viewManagerModel;
    this.makeInvitationViewModel = makeInvitationViewModel;
    this.myInvitationsViewName = myInvitationsViewName;
  }

  /**
   * handles to switch back after clicking the back button.
   */
  public void onBack() {
    makeInvitationViewModel.setState(new MakeInvitationState());
    makeInvitationViewModel.firePropertyChange();

    viewManagerModel.setState(myInvitationsViewName);
    viewManagerModel.firePropertyChange();
  }
}