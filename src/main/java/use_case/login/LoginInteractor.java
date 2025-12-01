package use_case.login;

import entity.User;
import use_case.make_invitation.CurrentUserGateway;
import interface_adapter.make_invitation.SessionCurrentUserGateway;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;
    private final SessionCurrentUserGateway sessionCurrentUserGateway;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary,
                           SessionCurrentUserGateway sessionCurrentUserGateway) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
        this.sessionCurrentUserGateway = sessionCurrentUserGateway;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();
        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
            final String pwd = userDataAccessObject.get(username).getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {

                final User user = userDataAccessObject.get(loginInputData.getUsername());

                userDataAccessObject.setCurrentUsername(username);

                final LoginOutputData loginOutputData = new LoginOutputData(user.getName());

                sessionCurrentUserGateway.setCurrentUser(user);

                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}
