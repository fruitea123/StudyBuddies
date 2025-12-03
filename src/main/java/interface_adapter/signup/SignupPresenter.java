package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

import java.util.Collections;

/**
 * The Presenter for the Signup Use Case.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private final ProfileViewModel profileViewModel;
    private final ViewManagerModel viewManagerModel;

    public SignupPresenter(ViewManagerModel viewManagerModel,
                           SignupViewModel signupViewModel,
                           ProfileViewModel profileViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.profileViewModel = profileViewModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData response) {
        // On success, switch to the profile view.
        final ProfileState state = profileViewModel.getState();
        state.setUsername(response.getEmail());
        state.setName(response.getFirstName() + " " + response.getLastName());
        state.setPrograms(Collections.singletonList(String.join(", ", response.getPrograms())));
        state.setDescription(response.getDescription());
        state.setIcon("/icons/" + response.getIcon() + ".png");

        profileViewModel.firePropertyChange();

        viewManagerModel.setState(profileViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setEmailError(error);
        signupViewModel.firePropertyChange();
    }

    @Override
    public void switchToProfileView() {
        viewManagerModel.setState(profileViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

}
