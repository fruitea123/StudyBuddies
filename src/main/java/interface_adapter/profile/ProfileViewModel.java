package interface_adapter.profile;

import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;

public class ProfileViewModel extends ViewModel<ProfileState> {

    public static final String VIEW_NAME = "profile";
    public static final String USERNAME_LABEL = "Username:";
    public static final String NAME_LABEL = "Name:";
    public static final String PROGRAM_LABEL = "Programs:";
    public static final String DESCRIPTION_LABEL = "Description:";

    public ProfileViewModel(String viewName) {
        super(VIEW_NAME);
        setState(new ProfileState());
    }

    public String getViewName() { return "profile"; }

    @Override
    public ProfileState getState() { return super.getState(); }

    @Override
    public void firePropertyChange() { super.firePropertyChange(); }
}
