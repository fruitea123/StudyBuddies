package interface_adapter.profile;

import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;

public class ProfileViewModel extends ViewModel<profileState> {
    public ProfileViewModel(String viewName) {
        super("profile");
        setState(new profileState());
    }

//    public String getViewName() {
//    }
//
//    public profileState getState() {
//    }
//
//    public void firePropertyChange() {
//    }
}
