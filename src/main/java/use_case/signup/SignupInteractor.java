package use_case.signup;

import entity.User;
import entity.UserFactory;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupPasswordHasher passwordHasher;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupPasswordHasher passwordHasher,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.passwordHasher = passwordHasher;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        if (userDataAccessObject.existsByEmail(signupInputData.getEmail())) {
            userPresenter.prepareFailView("User already exists.");
        }
        else if (!signupInputData.getPassword1().equals(signupInputData.getPassword2())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else if ("".equals(signupInputData.getPassword1())) {
            userPresenter.prepareFailView("New password cannot be empty");
        }
        else if ("".equals(signupInputData.getEmail())) {
            userPresenter.prepareFailView("Username cannot be empty");
        }
        else {

            String hashedPassword = passwordHasher.hashPassword(signupInputData.getPassword1());

            final User user = userFactory.create(
                    signupInputData.getEmail(),
                    hashedPassword,
                    signupInputData.getFirstName(),
                    signupInputData.getLastName(),
                    signupInputData.getPrograms(),
                    signupInputData.getPfp(),
                    signupInputData.getDescription());

            userDataAccessObject.save(user);

            final SignupOutputData signupOutputData = new SignupOutputData(
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPrograms(),
                    user.getIcon(),
                    user.getDescription());

            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToProfileView();
    }
}
