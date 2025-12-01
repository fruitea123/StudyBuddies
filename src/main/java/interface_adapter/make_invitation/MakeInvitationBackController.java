package interface_adapter.make_invitation;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;

// I wrote this file for switching the view to profile(currently logged in view) after invitation successfully created
public class MakeInvitationBackController {

    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;

    public MakeInvitationBackController(ViewManagerModel viewManagerModel,
                                        LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    public void onBack() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}