package interface_adapter.profile;

import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;

public class ProfileViewModel extends ViewModel<ProfileState> {

    public static final String USERNAME_LABEL = "Username";
    public static final String NAME_LABEL = "Name";
    public static final String P1_LABEL = "1st Program";
    public static final String P2_LABEL = "2nd Program";
    public static final String P3_LABEL = "3rd Program";
    public static final String DESCRIPTION_LABEL = "Description (optional)";
    public static final String PROGRAM_LABEL = "sign up";

    public ProfileViewModel(String viewName) {
        super("profile");
        setState(new ProfileState());
    }

    public String getViewName() { return "profile"; }

    @Override
    public ProfileState getState() { return super.getState(); }

    @Override
    public void firePropertyChange() { super.firePropertyChange(); }
}
