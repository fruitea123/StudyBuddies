package interface_adapter.make_invitation;

import interface_adapter.ViewManagerModel;
//import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.profile.ProfileViewModel;

// I wrote this file for switching the view to profile(currently logged in view) after invitation successfully created
public class MakeInvitationBackController {

    private final ViewManagerModel viewManagerModel;
    private final ProfileViewModel profileViewModel;

    public MakeInvitationBackController(ViewManagerModel viewManagerModel,
                                        ProfileViewModel profileViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.profileViewModel = profileViewModel;
    }

    public void onBack() {
        viewManagerModel.setState(profileViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}