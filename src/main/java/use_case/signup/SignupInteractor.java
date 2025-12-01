package use_case.signup;

import entity.User;
import entity.UserFactory;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    /* checks if:
    - All required fields are not empty
     - User already exists
     - Email contains @utoronto.ca
     - Password is at least 8 characters, contains one uppercase, one lowercase, and one number
     - Password is equal to repeatPassword
     - dateOfBirth must show that the user is at least 16 years old
     - numPrograms must be at least one
     - numPrograms must be equal to the number of programs the user is enrolled in
     */
    @Override
    public void execute(SignupInputData signupInputData) {

        LocalDate today = LocalDate.now();
        if ("".equals(signupInputData.getEmail())) {
            userPresenter.prepareFailView("This field cannot be empty");
        } else if ("".equals(signupInputData.getPassword())) {
            userPresenter.prepareFailView("This field cannot be empty");
        } else if ("".equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("This field cannot be empty");
        } else if ("".equals(signupInputData.getFirstName())) {
            userPresenter.prepareFailView("This field cannot be empty");
        } else if ("".equals(signupInputData.getLastName())) {
            userPresenter.prepareFailView("This field cannot be empty");
        } else if ("".equals(signupInputData.getDateOfBirth())) {
            userPresenter.prepareFailView("This field cannot be empty");
        } else if ("".equals(signupInputData.getNumPrograms())) {
            userPresenter.prepareFailView("This field cannot be empty");
        } else if ("".equals(signupInputData.getProgram1())) {
            userPresenter.prepareFailView("This field cannot be empty");
        } else if ("".equals(signupInputData.getProgram2()) && signupInputData.getNumPrograms() > 1) {
            userPresenter.prepareFailView("This field cannot be empty");
        } else if ("".equals(signupInputData.getProgram3()) && signupInputData.getNumPrograms() > 2) {
            userPresenter.prepareFailView("This field cannot be empty");
        } else if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            userPresenter.prepareFailView("User already exists");
        } else if (!signupInputData.getEmail().contains("@utoronto.ca")) {
            userPresenter.prepareFailView("Email address must be a UofT email address");
        } else if (signupInputData.getPassword().length() < 8) {
            userPresenter.prepareFailView("Password must be at least 8 characters");
        } else if (!signupInputData.getPassword().matches(".*[A-Z].*")) {
            userPresenter.prepareFailView("Password must contain at least 1 uppercase letter");
        } else if (!signupInputData.getPassword().matches(".*[a-z].*")) {
            userPresenter.prepareFailView("Password must contain at least 1 lowercase letter");
        } else if (!signupInputData.getPassword().matches(".*[0-9].*")) {
            userPresenter.prepareFailView("Password must contain at least 1 number");
        } else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match");
        } else if (Period.between(signupInputData.getDateOfBirth(), today).getYears() < 16) {
            userPresenter.prepareFailView("You must be at least 16 years old");
        } else if (signupInputData.getNumPrograms() < 1) {
            userPresenter.prepareFailView("Number of programs must be at least 1");
        } else {
            final User user = userFactory.create(signupInputData.getUsername(), signupInputData.getPassword());
            userDataAccessObject.save(user);

            final SignupOutputData signupOutputData = new SignupOutputData(user.getName());
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToProfileView() {
        userPresenter.switchToProfileView();
    }
}
