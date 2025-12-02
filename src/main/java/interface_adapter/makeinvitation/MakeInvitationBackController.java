package interface_adapter.makeinvitation;

import interface_adapter.ViewManagerModel;
import interface_adapter.profile.ProfileViewModel;

/**
 * Controller for switching back to the profile view after invitation successfully created.
 */
public class MakeInvitationBackController {

  private final ViewManagerModel viewManagerModel;
  private final ProfileViewModel profileViewModel;

  /**
   * controller for switching back.
   *
   * @param viewManagerModel view manager model
   * @param profileViewModel profile view model
   */
  public MakeInvitationBackController(ViewManagerModel viewManagerModel,
                                      ProfileViewModel profileViewModel) {
    this.viewManagerModel = viewManagerModel;
    this.profileViewModel = profileViewModel;
  }

  /**
   * handles to switch back after clicking the back button.
   */
  public void onBack() {
    viewManagerModel.setState(profileViewModel.getViewName());
    viewManagerModel.firePropertyChange();
  }
}