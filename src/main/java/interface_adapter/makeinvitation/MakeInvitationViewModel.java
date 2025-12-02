package interface_adapter.makeinvitation;

import interface_adapter.ViewModel;

/**
 * view model for make invitation
 * This class stores the MakeInvitationState used by the MakeInvitationView.
 * It also exposes the view name that ViewManagerModel uses to switch to this view.
 */
public class MakeInvitationViewModel extends ViewModel<MakeInvitationState> {

  // for ViewManager/view
  public static final String VIEW_NAME = "make invitation";

  // title for view
  public static final String TITLE = "Make Invitation";

  /**
   * create a new make invitation view model with an empty make invitation state.
   */
  public MakeInvitationViewModel() {
    super(VIEW_NAME);
    this.setState(new MakeInvitationState());
  }

}