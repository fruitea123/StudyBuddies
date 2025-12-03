package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;
import view.SignUpView;

/**
 * The controller for the Login Use Case.
 */
public class LoginController {

    private final LoginInputBoundary loginUseCaseInteractor;
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;
    private final SignUpView signUpView;

    public LoginController(LoginInputBoundary loginUseCaseInteractor,
                           ViewManagerModel viewManagerModel,
                           SignupViewModel signupViewModel, SignUpView signUpView) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.signUpView = signUpView;
    }

    /**
     * Executes the Login Use Case.
     * @param username the username of the user logging in
     * @param password the password of the user logging in
     */
    public void execute(String username, String password) {
        final LoginInputData loginInputData = new LoginInputData(username, password);
        loginUseCaseInteractor.execute(loginInputData);
    }

    /**
     * Switch to the Signup view.
     */
    public void switchToSignupView() {
        // LoginViewModel 继承的 ViewModel 里已经设置 view name = "log in"
        // SignupViewModel 也应该类似：super("sign up");
        viewManagerModel.setState(signUpView.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
